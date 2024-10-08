package ru.lunchvoting.restaurant.util;

import ru.lunchvoting.restaurant.model.Restaurant;
import ru.lunchvoting.restaurant.to.RestaurantWithDishesTo;

import java.util.List;

public class RestaurantUtil {

    public static List<RestaurantWithDishesTo> toWithDishes(List<Restaurant> restaurants) {
        return restaurants.stream().map(RestaurantUtil::toWithDishes).toList();
    }

    public static RestaurantWithDishesTo toWithDishes(Restaurant restaurant) {
        return new RestaurantWithDishesTo(restaurant.getId(), restaurant.getName(), restaurant.getMenu());
    }
}
