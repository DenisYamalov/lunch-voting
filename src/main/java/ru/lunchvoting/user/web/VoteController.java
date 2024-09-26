package ru.lunchvoting.user.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.lunchvoting.app.AuthUser;
import ru.lunchvoting.user.repository.VoteRepository;
import ru.lunchvoting.user.service.VoteService;
import ru.lunchvoting.user.to.VoteResult;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = VoteController.VOTE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    static final String VOTE_URL = "/api/votes";

    private VoteService service;
    private VoteRepository repository;

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
    @Operation(summary = "Vote/update vote for restaurant",
            description = "Vote/update vote for specified restaurant by id")
    @Transactional
    public void vote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("user id = {} voting for restaurant id = {}", authUser.id(), id);
        service.save(authUser, id);
    }
}
