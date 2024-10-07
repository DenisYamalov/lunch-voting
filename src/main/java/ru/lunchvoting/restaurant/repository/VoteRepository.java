package ru.lunchvoting.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.common.BaseRepository;
import ru.lunchvoting.restaurant.model.Vote;
import ru.lunchvoting.restaurant.to.VoteResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.user.id=:id AND v.voteDate=:voteDate")
    Optional<Vote> findByUserIdAndVoteDate(int id, LocalDate voteDate);

    @Query("""
            SELECT NEW ru.lunchvoting.restaurant.to.VoteResult(v.restaurant.id, count(v.id))
            FROM Vote v WHERE v.voteDate=:voteDate GROUP BY v.restaurant.id ORDER BY count(v.id) DESC""")
    List<VoteResult> getResults(LocalDate voteDate);

    List<Vote> findAllByUserId(int userId);
}
