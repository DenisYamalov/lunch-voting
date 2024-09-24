package ru.lunchvoting.user.web;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lunchvoting.user.model.Restaurant;
import ru.lunchvoting.user.repository.RestaurantRepository;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.RESTAURANT_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController {
    static final String RESTAURANT_URL = "/api/restaurants";

    RestaurantRepository restaurantRepository;

    //TODO count db queries
    @GetMapping
    @Operation(summary = "Get list of restaurants")
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get restaurant",
            description = "Get restaurant by id")
    public Restaurant get(@PathVariable int id) {
        log.info("get {}", id);
        return restaurantRepository.getExisted(id);
    }
}
