package ru.lunchvoting.restaurant;

import ru.lunchvoting.MatcherFactory;
import ru.lunchvoting.restaurant.model.Vote;
import ru.lunchvoting.restaurant.to.VoteResult;
import ru.lunchvoting.restaurant.to.VoteTo;
import ru.lunchvoting.restaurant.util.VoteUtil;

import java.time.LocalDate;

import static ru.lunchvoting.restaurant.RestaurantTestData.*;
import static ru.lunchvoting.user.UserTestData.USER;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingEqualsComparator(Vote.class);
    public static final MatcherFactory.Matcher<VoteResult> VOTE_RESULT_MATCHER =
            MatcherFactory.usingEqualsComparator(VoteResult.class);
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(VoteTo.class);

    public static final int USER_VOTE_ID = 1;
    public static final int ADMIN_VOTE_ID = 2;
    public static final int SECOND_USER_VOTE_ID = 3;

    public static final VoteResult VOTE_KFC_RESULT = new VoteResult(KFC_ID, 1);
    public static final VoteResult VOTE_MCDONALDS_RESULT = new VoteResult(MCDONALDS_ID, 2);

    public static final Vote USER_VOTE = new Vote(USER_VOTE_ID, USER, KFC, LocalDate.now());
    public static final VoteTo USER_VOTE_TO = VoteUtil.toVoteTo(USER_VOTE);
}
