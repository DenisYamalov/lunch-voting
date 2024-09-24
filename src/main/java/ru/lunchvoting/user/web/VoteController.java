package ru.lunchvoting.user.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.lunchvoting.app.AuthUser;
import ru.lunchvoting.common.error.IllegalRequestDataException;
import ru.lunchvoting.user.model.Vote;
import ru.lunchvoting.user.repository.RestaurantRepository;
import ru.lunchvoting.user.repository.VoteRepository;
import ru.lunchvoting.user.to.VoteResult;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = VoteController.VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String VOTE_URL = "/api/votes";

    VoteRepository repository;
    RestaurantRepository restaurantRepository;
    private final Clock clock;

    /**
     * @return restaurant id
     */
    @GetMapping
    @Operation(summary = "Get vote",
            description = "Get voted restaurant id")
    public int getVote(@AuthenticationPrincipal AuthUser authUser) {
        return repository.getExisted(authUser.id()).getRestaurant().getId();
    }

    @GetMapping("/results")
    @Operation(summary = "Get vote results",
            description = "Get vote count for each restaurant on specified date")
    public List<VoteResult> getVoteResults(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<VoteResult> results = repository.getResults(date);
        log.info("get vote results = {}", results);
        return results;
    }

    @PostMapping("/{id}")
    @Operation(summary = "Vote for restaurant",
            description = "Vote for specified restaurant by id")
    public void vote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("user id = {} voting for restaurant id = {}", authUser.id(), id);
        save(authUser, id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update vote",
            description = "Update vote for specified restaurant by id")
    public void updateVote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("user id = {} updating vote for restaurant id = {}", authUser.id(), id);
        save(authUser, id);
    }

    private void save(AuthUser authUser, int id) {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDate today = now.toLocalDate();
        Vote vote;

        if (now.isBefore(today.atTime(11, 0))) {
            Optional<Vote> todayVote = repository.findByUserIdAndVoteDate(authUser.id(), today);

            if (todayVote.isPresent()) {
                vote = todayVote.get();
                vote.setRestaurant(restaurantRepository.getExisted(id));
                vote.setVoteDate(today);
            } else {
                vote = new Vote(null, authUser.getUser(), restaurantRepository.getExisted(id), today);
            }
        } else {
            throw new IllegalRequestDataException("it is too late, vote can't be done after 11:00");
        }
        repository.save(vote);
    }
}
