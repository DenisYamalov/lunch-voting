package ru.lunchvoting.user.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.lunchvoting.user.model.Dish;
import ru.lunchvoting.user.repository.DishRepository;
import ru.lunchvoting.user.repository.RestaurantRepository;

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
    public void create(@PathVariable int restaurantId, @RequestBody Dish dish) {
        log.info("create {} for restaurant id = {}", dish, restaurantId);
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        dishRepository.save(dish);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete {} for restaurant id = {}", id, restaurantId);
        Dish dish = dishRepository.getBelonged(restaurantId, id);
        dishRepository.delete(dish);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update {} for restaurant id = {}", dish, restaurantId);
        assureIdConsistent(dish, id);
        dishRepository.getBelonged(restaurantId, id);
        dish.setRestaurant(restaurantRepository.getExisted(restaurantId));
        dishRepository.save(dish);
    }
}