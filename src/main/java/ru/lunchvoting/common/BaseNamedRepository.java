package ru.lunchvoting.common;

import org.springframework.data.repository.NoRepositoryBean;
import ru.lunchvoting.common.model.NamedEntity;

import java.util.Optional;

@NoRepositoryBean
public interface BaseNamedRepository<T extends NamedEntity> extends BaseRepository<T> {
    Optional<T> findByName(String name);
}
