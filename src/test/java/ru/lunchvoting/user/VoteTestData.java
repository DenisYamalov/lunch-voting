package ru.lunchvoting.user;

import ru.lunchvoting.MatcherFactory;
import ru.lunchvoting.restaurant.to.VoteResult;

import static ru.lunchvoting.restaurant.RestaurantTestData.KFC_ID;
import static ru.lunchvoting.restaurant.RestaurantTestData.MCDONALDS_ID;

public class VoteTestData {

    public static final MatcherFactory.Matcher<VoteResult> VOTE_RESULT_MATCHER =
            MatcherFactory.usingEqualsComparator(VoteResult.class);

    public static final int USER_VOTE_ID = 1;
    public static final int ADMIN_VOTE_ID = 2;
    public static final int SECOND_USER_VOTE_ID = 3;

    public static final VoteResult VOTE_KFC_RESULT = new VoteResult(KFC_ID, 1);
    public static final VoteResult VOTE_MCDONALDS_RESULT = new VoteResult(MCDONALDS_ID, 2);
}
