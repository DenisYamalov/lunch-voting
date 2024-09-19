package ru.lunchvoting.user.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.common.BaseRepository;
import ru.lunchvoting.user.model.Vote;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
}
