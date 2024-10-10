package ru.lunchvoting.vote.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.lunchvoting.app.AuthUser;
import ru.lunchvoting.common.error.IllegalRequestDataException;
import ru.lunchvoting.vote.model.Vote;
import ru.lunchvoting.restaurant.repository.RestaurantRepository;
import ru.lunchvoting.vote.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static ru.lunchvoting.common.validation.ValidationUtil.assureIdConsistent;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {

    private VoteRepository repository;
    private RestaurantRepository restaurantRepository;
    static final int VOTE_HOUR = 11;
    static final int VOTE_MIN = 0;

    public Vote create(AuthUser authUser, int restaurantId) {
        if (getVoteFromRepo(authUser).isPresent()) {
            throw new IllegalRequestDataException("User already voted");
        }
        Vote vote = new Vote(null, authUser.getUser(), null, LocalDate.now());
        return save(vote, restaurantId);
    }

    public void update(AuthUser authUser, int restaurantId, int id) {
        Optional<Vote> voteFromRepo = getVoteFromRepo(authUser);
        if (voteFromRepo.isPresent()) {
            Vote vote = voteFromRepo.get();
            assureIdConsistent(vote, id);
            LocalDate today = LocalDate.now();
            if (LocalDateTime.now().isBefore(today.atTime(VOTE_HOUR, VOTE_MIN))) {
                save(vote, restaurantId);
            } else {
                throw new IllegalRequestDataException("it is too late, vote can't be changed after 11:00");
            }
        } else {
            throw new IllegalRequestDataException("User did not vote today");
        }
    }

    private Vote save(Vote vote, int restaurantId) {
        vote.setRestaurant(restaurantRepository.getReferenceById(restaurantId));
        log.info("save vote = {}", vote);
        return repository.save(vote);
    }

    private Optional<Vote> getVoteFromRepo(AuthUser authUser) {
        LocalDate today = LocalDate.now();
        return repository.findByUserIdAndVoteDate(authUser.id(), today);
    }

}
