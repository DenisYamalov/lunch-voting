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
import ru.lunchvoting.restaurant.model.Restaurant;
import ru.lunchvoting.restaurant.repository.RestaurantRepository;

import java.net.URI;

import static ru.lunchvoting.common.validation.ValidationUtil.assureIdConsistent;
import static ru.lunchvoting.common.validation.ValidationUtil.checkNew;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping(value = RestaurantAdminController.RESTAURANT_ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@CacheConfig(cacheNames = "restaurants")
public class RestaurantAdminController {
    static final String RESTAURANT_ADMIN_URL = "/api/admin/restaurants";

    RestaurantRepository restaurantRepository;

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete restaurant")
    @Caching(evict = {@CacheEvict(cacheNames = "allRestaurants", allEntries = true), @CacheEvict(key = "#id")})
    public void delete(@PathVariable int id) {
        log.info("delete {}", id);
        restaurantRepository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create restaurant")
    @CacheEvict(cacheNames = "allRestaurants", allEntries = true)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(RESTAURANT_ADMIN_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update restaurant",
            description = "Update restaurant with specified id")
    @Caching(evict = {@CacheEvict(cacheNames = "allRestaurants", allEntries = true), @CacheEvict(key = "#id")})
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        restaurantRepository.save(restaurant);
    }
}
