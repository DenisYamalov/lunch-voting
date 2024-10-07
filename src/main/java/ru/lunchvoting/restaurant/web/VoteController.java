package ru.lunchvoting.restaurant.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lunchvoting.app.AuthUser;
import ru.lunchvoting.common.error.IllegalRequestDataException;
import ru.lunchvoting.restaurant.model.Vote;
import ru.lunchvoting.restaurant.repository.VoteRepository;
import ru.lunchvoting.restaurant.service.VoteService;
import ru.lunchvoting.restaurant.to.VoteTo;
import ru.lunchvoting.restaurant.util.VoteUtil;

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

    /**
     * @return restaurant id
     */
    @GetMapping("/today")
    @Operation(summary = "Get vote",
            description = "Get voted restaurant id")
    public VoteTo getVote(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get vote for user id = {}", authUser.id());
        Vote existed = repository.findByUserIdAndVoteDate(authUser.id(), LocalDate.now())
                .orElseThrow(() -> new IllegalRequestDataException("User didn't vote today"));
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
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(VOTE_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update vote for restaurant",
            description = "Update vote with id")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@AuthenticationPrincipal AuthUser authUser,
                           @RequestBody int restaurantId,
                           @PathVariable int id) {
        log.info("user id = {} updates vote, new restaurant id = {}", authUser.id(), restaurantId);
        service.update(authUser, restaurantId, id);
    }
}
