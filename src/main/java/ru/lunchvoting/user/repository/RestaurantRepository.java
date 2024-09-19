package ru.lunchvoting.user.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.common.BaseNamedRepository;
import ru.lunchvoting.user.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseNamedRepository<Restaurant> {
}
