package ru.lunchvoting.user.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.app.AuthUser;
import ru.lunchvoting.common.error.IllegalRequestDataException;
import ru.lunchvoting.user.model.Restaurant;
import ru.lunchvoting.user.model.Vote;
import ru.lunchvoting.user.repository.RestaurantRepository;
import ru.lunchvoting.user.repository.VoteRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {
    private VoteRepository repository;
    private RestaurantRepository restaurantRepository;
    private final Clock clock;

    //TODO prevent restaurant_id in at sql
    @Transactional
    public void save(AuthUser authUser, int id) {
        LocalDateTime now = LocalDateTime.now(clock);
        LocalDate today = now.toLocalDate();
        Vote vote;

        if (now.isBefore(today.atTime(11, 0))) {
            Optional<Vote> todayVote = repository.findByUserIdAndVoteDate(authUser.id(), today);
            Restaurant restaurant = restaurantRepository.getExisted(id);

            if (todayVote.isPresent()) {
                vote = todayVote.get();
                vote.setRestaurant(restaurant);
                vote.setVoteDate(today);
            } else {
                vote = new Vote(null, authUser.getUser(), restaurant, today);
            }
        } else {
            throw new IllegalRequestDataException("it is too late, vote can't be done after 11:00");
        }
        repository.save(vote);
    }
}
