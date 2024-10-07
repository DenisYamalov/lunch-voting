package ru.lunchvoting.restaurant.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.lunchvoting.restaurant.model.Dish;
import ru.lunchvoting.restaurant.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

//TODO add price description
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
        log.info("Get all dishes for restaurant {}", restaurantId);
        return dishRepository.getAllByRestaurantIdOrderByMenuDateDesc(restaurantId);
    }

    @GetMapping("/by-date")
    @Operation(summary = "Get dishes",
            description = "Get list of dishes for specified restaurant")
    @Cacheable(cacheNames = "allDishes")
    public List<Dish> getAllByDate(@PathVariable int restaurantId,
                                   @RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("Get dishes for restaurant {} by date {}", restaurantId, date);
        if (date == null) {
            date = LocalDate.now();
        }
        return dishRepository.getAllByRestaurantIdAndMenuDate(restaurantId, date);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get dish",
            description = "Get dish by id for specified restaurant")
    @Cacheable(key = "#id")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        log.info("Get dish {} for restaurant {}", id, restaurantId);
        return dishRepository.getBelonged(restaurantId, id);
    }
}
