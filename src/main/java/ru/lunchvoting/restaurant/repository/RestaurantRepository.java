package ru.lunchvoting.restaurant.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.common.BaseRepository;
import ru.lunchvoting.restaurant.model.Restaurant;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {
}
