package ru.lunchvoting.user.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.common.BaseRepository;
import ru.lunchvoting.user.model.Dish;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {
}
