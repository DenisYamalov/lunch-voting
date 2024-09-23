package ru.lunchvoting.user.web;

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

    @GetMapping
    public List<VoteResult> getVoteResults(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<VoteResult> results = repository.getResults(date);
        log.info("get vote results = {}", results);
        return results;
    }

    @PostMapping("/{id}")
    public void vote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("user id = {} voting for restaurant id = {}", authUser.id(), id);
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        Optional<Vote> todayVote = repository.findByUserIdAndDateTimeAfter(authUser.id(), today.atStartOfDay());
        Vote vote;

        if (now.isBefore(today.atTime(11, 0))) {
            if (todayVote.isPresent()) {
                vote = todayVote.get();
                vote.setRestaurant(restaurantRepository.getExisted(id));
                vote.setDateTime(now);
            } else {
                vote = new Vote(null, authUser.getUser(), restaurantRepository.getExisted(id), LocalDateTime.now());
            }
        } else {
            throw new IllegalRequestDataException("it is too late, vote can't be done after 11:00");
        }
        repository.save(vote);
    }
}
