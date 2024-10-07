package ru.lunchvoting.restaurant.util;

import ru.lunchvoting.restaurant.model.Vote;
import ru.lunchvoting.restaurant.to.RestaurantTo;
import ru.lunchvoting.restaurant.to.VoteTo;

import java.util.Collection;
import java.util.List;

public class VoteUtil {

    public static VoteTo toVoteTo(Vote vote) {
        return new VoteTo(vote.getId(), new RestaurantTo(vote.getRestaurant().getId()), vote.getVoteDate());
    }

    public static List<VoteTo> toVoteTo(Collection<Vote> votes) {
        return votes.stream().map(VoteUtil::toVoteTo).toList();
    }
}
