package ru.lunchvoting.user.util;

import lombok.experimental.UtilityClass;
import ru.lunchvoting.user.model.Dish;
import ru.lunchvoting.user.model.Restaurant;
import ru.lunchvoting.user.to.DishTo;

@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo, Restaurant restaurant) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getMenuDate(), dishTo.getPrice(), restaurant);
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo, Restaurant restaurant) {
        dish.setPrice(dishTo.getPrice());
        dish.setMenuDate(dishTo.getMenuDate());
        dish.setName(dishTo.getName());
        dish.setRestaurant(restaurant);
        return dish;
    }
}
