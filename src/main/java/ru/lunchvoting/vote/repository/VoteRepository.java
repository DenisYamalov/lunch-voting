package ru.lunchvoting.vote.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.common.BaseRepository;
import ru.lunchvoting.common.error.DataConflictException;
import ru.lunchvoting.vote.model.Vote;
import ru.lunchvoting.vote.to.VoteResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.user.id=:id AND v.voteDate=:voteDate")
    Optional<Vote> findByUserIdAndVoteDate(int id, LocalDate voteDate);

    @Query("""
            SELECT NEW ru.lunchvoting.vote.to.VoteResult(v.restaurant.id, count(v.id), v.voteDate)
            FROM Vote v WHERE v.voteDate=:voteDate GROUP BY v.restaurant.id ORDER BY count(v.id) DESC""")
    List<VoteResult> getResultsByDate(LocalDate voteDate);

    List<Vote> findAllByUserId(int userId);

    @Query("SELECT v FROM Vote v WHERE v.id = :id and v.user.id = :userId")
    Optional<Vote> get(int userId, int id);

    default Vote getBelonged(int userId, int id) {
        return get(userId, id).orElseThrow(
                () -> new DataConflictException("Vote id =" + id +
                                                " is not exist or doesn't belong to User id=" + userId));
    }
}
