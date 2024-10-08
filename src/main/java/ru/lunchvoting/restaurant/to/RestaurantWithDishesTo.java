package ru.lunchvoting.restaurant.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.lunchvoting.common.to.NamedTo;
import ru.lunchvoting.restaurant.model.Dish;

import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class RestaurantWithDishesTo extends NamedTo {

    @NotNull
    List<Dish> menu;

    public RestaurantWithDishesTo(Integer id, String name, List<Dish> menu) {
        super(id, name);
        this.menu = menu;
    }
}
