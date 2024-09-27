package ru.lunchvoting.restaurant.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lunchvoting.restaurant.model.Dish;
import ru.lunchvoting.restaurant.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = DishController.DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "dishes")
public class DishController {

    static final String DISH_URL = RestaurantController.RESTAURANT_URL + "/{restaurantId}/dishes";

    DishRepository dishRepository;

    @GetMapping
    @Operation(summary = "Get dishes",
            description = "Get list of dishes for specified restaurant")
    @Cacheable(cacheNames = "allDishes")
    public List<Dish> getAll(@PathVariable int restaurantId) {
        return dishRepository.getAllByRestaurantIdAndMenuDate(restaurantId, LocalDate.now());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get dish",
            description = "Get dish by id for specified restaurant")
    @Cacheable(key = "#id")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        return dishRepository.getBelonged(restaurantId, id);
    }
}
