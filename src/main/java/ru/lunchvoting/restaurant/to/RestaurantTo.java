package ru.lunchvoting.restaurant.to;

import lombok.*;
import ru.lunchvoting.common.to.BaseTo;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
public class RestaurantTo extends BaseTo {
    public RestaurantTo(Integer id) {
        super(id);
    }
}
