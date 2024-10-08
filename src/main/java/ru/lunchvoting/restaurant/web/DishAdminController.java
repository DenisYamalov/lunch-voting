package ru.lunchvoting.restaurant.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lunchvoting.restaurant.model.Dish;
import ru.lunchvoting.restaurant.repository.DishRepository;
import ru.lunchvoting.restaurant.repository.RestaurantRepository;
import ru.lunchvoting.restaurant.to.DishTo;
import ru.lunchvoting.restaurant.util.DishUtil;

import java.net.URI;

import static ru.lunchvoting.common.validation.ValidationUtil.assureIdConsistent;
import static ru.lunchvoting.common.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishAdminController.DISH_ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@CacheConfig(cacheNames = "dishes")
public class DishAdminController {

    static final String DISH_ADMIN_URL = RestaurantAdminController.RESTAURANT_ADMIN_URL + "/{restaurantId}/dishes";

    DishRepository dishRepository;
    RestaurantRepository restaurantRepository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete dish",
            description = "Delete dish with specified id for restaurant with specified id")
    @Caching(evict = {@CacheEvict(cacheNames = "allDishes", allEntries = true), @CacheEvict(key = "#id")})
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete {} for restaurant id = {}", id, restaurantId);
        dishRepository.getBelonged(restaurantId, id);
        dishRepository.deleteExisted(id);
    }

    @PostMapping
    @Operation(summary = "Create dish",
            description = "Create new dish for restaurant with specified id. There could be only one dish with same " +
                          "name, price, date at one restaurant")
    @CacheEvict(cacheNames = "allDishes", allEntries = true)
    public ResponseEntity<Dish> create(@PathVariable int restaurantId, @Valid @RequestBody DishTo dishTo) {
        log.info("create {} for restaurant id = {}", dishTo, restaurantId);
        checkNew(dishTo);
        Dish dish = DishUtil.createNewFromTo(dishTo, restaurantRepository.getExisted(restaurantId));
        Dish created = dishRepository.save(dish);
        log.info("Created new {}", created);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DISH_ADMIN_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update dish",
            description = "Update dish with specified id for restaurant with specified id")
    @Caching(evict = {@CacheEvict(cacheNames = "allDishes", allEntries = true), @CacheEvict(key = "#id")})
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update {} for restaurant id = {}", dishTo, restaurantId);
        assureIdConsistent(dishTo, id);
        Dish dish = DishUtil.updateFromTo(dishRepository.getBelonged(restaurantId, id), dishTo,
                                          restaurantRepository.getExisted(restaurantId));
        dishRepository.save(dish);
    }
}