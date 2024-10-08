package ru.lunchvoting.restaurant.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.lunchvoting.restaurant.model.Restaurant;
import ru.lunchvoting.restaurant.repository.RestaurantRepository;
import ru.lunchvoting.restaurant.to.RestaurantWithDishesTo;
import ru.lunchvoting.restaurant.util.RestaurantUtil;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "restaurants")
public class RestaurantController {
    static final String RESTAURANT_URL = "/api/restaurants";

    private RestaurantRepository restaurantRepository;

    @GetMapping
    @Operation(summary = "Get list of restaurants")
    @Cacheable("allRestaurants")
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant",
            description = "Get restaurant by id")
    @Cacheable(key = "#id")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant with id={}", id);
        return restaurantRepository.getExisted(id);
    }

    @GetMapping("/with-dishes")
    @Operation(summary = "Get restaurants with dishes")
    public List<RestaurantWithDishesTo> getWithDishes(@RequestParam @Nullable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get restaurants with dishes for date={}", date);
        if (date == null) {
            date = LocalDate.now();
            log.info("set today date");
        }
        return RestaurantUtil.toWithDishes(restaurantRepository.getRestaurantsWithDishes(date));
    }
}
