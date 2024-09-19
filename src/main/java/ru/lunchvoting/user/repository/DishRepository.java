package ru.lunchvoting.user.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.common.BaseNamedRepository;
import ru.lunchvoting.user.model.Dish;

@Transactional(readOnly = true)
public interface DishRepository extends BaseNamedRepository<Dish> {
}
