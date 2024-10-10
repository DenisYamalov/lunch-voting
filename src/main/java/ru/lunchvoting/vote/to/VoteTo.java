package ru.lunchvoting.vote.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.lunchvoting.common.to.BaseTo;
import ru.lunchvoting.restaurant.to.RestaurantTo;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString
public class VoteTo extends BaseTo {
    @NotNull
    RestaurantTo restaurant;

    @NotNull
    LocalDate voteDate;

    public VoteTo(Integer id, RestaurantTo restaurantTo, LocalDate voteDate) {
        super(id);
        this.restaurant = restaurantTo;
        this.voteDate = voteDate;
    }
}
