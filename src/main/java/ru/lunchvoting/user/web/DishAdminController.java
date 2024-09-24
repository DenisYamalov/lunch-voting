package ru.lunchvoting.user.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.lunchvoting.user.model.Dish;
import ru.lunchvoting.user.repository.DishRepository;
import ru.lunchvoting.user.repository.RestaurantRepository;

import java.net.URI;

import static ru.lunchvoting.common.validation.ValidationUtil.assureIdConsistent;
import static ru.lunchvoting.common.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = DishAdminController.DISH_ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishAdminController {

    static final String DISH_ADMIN_URL = RestaurantAdminController.RESTAURANT_ADMIN_URL + "/{restaurantId}/dishes";

    DishRepository dishRepository;
    RestaurantRepository restaurantRepository;

    @PostMapping
    @Operation(summary = "Create dish",
            description = "Create new dish for restaurant with specified id")
    public ResponseEntity<Dish> create(@PathVariable int restaurantId, @Valid @RequestBody Dish dish) {
        log.info("create {} for restaurant id = {}", dish, restaurantId);
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        Dish created = dishRepository.save(dish);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(DISH_ADMIN_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete dish",
            description = "Delete dish with specified id for restaurant with specified id")
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete {} for restaurant id = {}", id, restaurantId);
        dishRepository.getBelonged(restaurantId, id);
        dishRepository.deleteExisted(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update dish",
            description = "Update dish with specified id for restaurant with specified id")
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update {} for restaurant id = {}", dish, restaurantId);
        assureIdConsistent(dish, id);
        dishRepository.getBelonged(restaurantId, id);
        dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        dishRepository.save(dish);
    }
}