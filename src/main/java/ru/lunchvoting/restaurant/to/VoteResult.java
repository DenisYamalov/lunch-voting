package ru.lunchvoting.restaurant.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class VoteResult {

    int restaurantId;
    long votesCount;
}
