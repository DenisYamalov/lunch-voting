package ru.lunchvoting.user.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lunchvoting.user.model.Dish;
import ru.lunchvoting.user.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = DishController.DISH_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class DishController {

    static final String DISH_URL = RestaurantController.RESTAURANT_URL + "/{restaurantId}/dishes";

    DishRepository dishRepository;

    @GetMapping
    public List<Dish> getAll(@PathVariable int restaurantId) {
        return dishRepository.getAllByRestaurantIdAndMenuDate(restaurantId, LocalDate.now());
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int restaurantId, @PathVariable int id) {
        return dishRepository.getBelonged(restaurantId, id);
    }
}
