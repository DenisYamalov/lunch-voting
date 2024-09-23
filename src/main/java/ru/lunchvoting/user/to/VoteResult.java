package ru.lunchvoting.user.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@EqualsAndHashCode
public class VoteResult {

    int restaurantId;
    long votesCount;
}
