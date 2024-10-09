package ru.lunchvoting.restaurant.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.common.BaseRepository;
import ru.lunchvoting.common.error.DataConflictException;
import ru.lunchvoting.restaurant.model.Dish;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    /**
     *
     * @param restaurantId id of restaurant
     * @param date date of menu
     * @return all dishes for specified restaurant and date
     */
    List<Dish> getAllByRestaurantIdAndMenuDate(int restaurantId, LocalDate date);

    /**
     *
     * @param restaurantId id of restaurant
     * @return all dishes for specified restaurant
     */
    List<Dish> getAllByRestaurantIdOrderByMenuDateDesc(int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.id = :id and d.restaurant.id = :restaurantId")
    Optional<Dish> get(int restaurantId, int id);

    default Dish getBelonged(int restaurantId, int id) {
        return get(restaurantId, id).orElseThrow(
                () -> new DataConflictException("Dish id =" + id +
                                                " is not exist or doesn't belong to Restaurant id=" + restaurantId));
    }
}
