package ru.lunchvoting.user.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.lunchvoting.app.AuthUser;
import ru.lunchvoting.common.error.IllegalRequestDataException;
import ru.lunchvoting.user.model.Restaurant;
import ru.lunchvoting.user.model.Vote;
import ru.lunchvoting.user.repository.RestaurantRepository;
import ru.lunchvoting.user.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = RestaurantController.RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    static final String RESTAURANT_URL = "/api/restaurants";

    RestaurantRepository restaurantRepository;
    VoteRepository voteRepository;

    //TODO count db queries
    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return restaurantRepository.getExisted(id);
    }

    @PostMapping("/{id}")
    public void vote(@AuthenticationPrincipal AuthUser authUser, @PathVariable int id) {
        log.info("user id = {} voting for restaurant id = {}", authUser.id(), id);
        LocalDateTime now = LocalDateTime.now();
        LocalDate today = LocalDate.now();
        Optional<Vote> todayVote = voteRepository.findByUserIdAndDateTimeAfter(authUser.id(), today.atStartOfDay());
        Vote vote;
        if (todayVote.isPresent()) {
            if (now.isBefore(today.atTime(11, 0))) {
                vote = todayVote.get();
                vote.setRestaurant(restaurantRepository.getExisted(id));
                vote.setDateTime(now);
            } else {
                throw new IllegalRequestDataException("it is too late, vote can't be changed");
            }
        } else {
            vote = new Vote(null, authUser.getUser(), restaurantRepository.getExisted(id), LocalDateTime.now());
        }
        voteRepository.save(vote);
    }
}
