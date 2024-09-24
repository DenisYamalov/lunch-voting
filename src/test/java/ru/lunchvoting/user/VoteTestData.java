package ru.lunchvoting.user;

import ru.lunchvoting.MatcherFactory;
import ru.lunchvoting.user.model.Vote;
import ru.lunchvoting.user.to.VoteResult;

import java.time.LocalDate;

import static ru.lunchvoting.user.RestaurantTestData.*;
import static ru.lunchvoting.user.UserTestData.*;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "dish");

    public static final MatcherFactory.Matcher<VoteResult> VOTE_RESULT_MATCHER =
            MatcherFactory.usingEqualsComparator(VoteResult.class);

    public static final int USER_VOTE_ID = 1;
    public static final int ADMIN_VOTE_ID = 2;
    public static final int SECOND_USER_VOTE_ID = 3;

    public static final Vote USER_VOTE = new Vote(USER_VOTE_ID, USER, KFC, LocalDate.now());
    public static final Vote ADMIN_VOTE = new Vote(ADMIN_VOTE_ID, ADMIN, MCDONALDS, LocalDate.now());
    public static final Vote SECOND_USER_VOTE = new Vote(SECOND_USER_VOTE_ID, SECOND_USER, MCDONALDS, LocalDate.now());

    public static final VoteResult VOTE_KFC_RESULT = new VoteResult(KFC_ID, 1);
    public static final VoteResult VOTE_MCDONALDS_RESULT = new VoteResult(MCDONALDS_ID, 2);
}
