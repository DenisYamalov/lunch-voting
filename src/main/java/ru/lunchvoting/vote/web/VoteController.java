package ru.lunchvoting.vote.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lunchvoting.app.AuthUser;
import ru.lunchvoting.common.error.IllegalRequestDataException;
import ru.lunchvoting.vote.model.Vote;
import ru.lunchvoting.vote.repository.VoteRepository;
import ru.lunchvoting.vote.service.VoteService;
import ru.lunchvoting.vote.to.VoteTo;
import ru.lunchvoting.vote.util.VoteUtil;

import java.net.URI;
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
     * @return list of all users votes
     */
    @GetMapping
    public List<VoteTo> getAll(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all votes for user id = {}", authUser.id());
        return VoteUtil.toVoteTo(repository.findAllByUserId(authUser.id()));
    }

    @GetMapping("{id}")
    @Operation(summary = "Get vote by id")
    public VoteTo getVoteByDate(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("get vote id = {}for user id = {}", id, authUser.id());
        Vote belonged = repository.getBelonged(authUser.id(), id);
        return VoteUtil.toVoteTo(belonged);
    }

    @GetMapping("/by-date")
    @Operation(summary = "Get vote",
            description = "Get voted restaurant id for specified date")
    public VoteTo getVoteByDate(@AuthenticationPrincipal AuthUser authUser,
                                @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get vote for user id = {} on date = {}", authUser.id(), date);
        LocalDate finalDate = date == null ? LocalDate.now() : date;
        Vote existed = repository.findByUserIdAndVoteDate(authUser.id(), finalDate)
                .orElseThrow(() -> new IllegalRequestDataException("User didn't vote on " + finalDate));
        return VoteUtil.toVoteTo(existed);
    }

    @PostMapping
    @Operation(summary = "Vote for restaurant",
            description = "Vote vote for specified restaurant by id")
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Vote> vote(@AuthenticationPrincipal AuthUser authUser, @RequestBody int restaurantId) {
        log.info("user id = {} voting for restaurant id = {}", authUser.id(), restaurantId);
        Vote created = service.create(authUser, restaurantId);
        log.info("created new {}", created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update vote for restaurant",
            description = "Update vote with id = {id}, request body should contain new restaurant id")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@AuthenticationPrincipal AuthUser authUser,
                           @Parameter(description = "restaurant id") @RequestBody int restaurantId,
                           @Parameter(description = "id of vote to update") @PathVariable int id) {
        log.info("user id = {} updates vote id = {}, new restaurant id = {}", authUser.id(), id, restaurantId);
        service.update(authUser, restaurantId, id);
    }
}
