package ru.lunchvoting.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.common.BaseRepository;
import ru.lunchvoting.user.model.Vote;
import ru.lunchvoting.user.to.VoteResult;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.user.id=:id AND v.voteDate=:voteDate")
    Optional<Vote> findByUserIdAndVoteDate(int id, LocalDate voteDate);

    @Query("""
            SELECT NEW ru.lunchvoting.user.to.VoteResult(v.restaurant.id, count(v.id))
            FROM Vote v WHERE v.voteDate=:voteDate GROUP BY v.restaurant.id""")
    List<VoteResult> getResults(LocalDate voteDate);
}
