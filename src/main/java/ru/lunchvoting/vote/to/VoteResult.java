package ru.lunchvoting.vote.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode
public class VoteResult {

    int restaurantId;
    long votesCount;
    LocalDate voteDate;
}
