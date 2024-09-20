package ru.lunchvoting.user;

import ru.lunchvoting.MatcherFactory;
import ru.lunchvoting.user.model.Vote;

import java.time.LocalDateTime;

import static ru.lunchvoting.user.RestaurantTestData.KFC;
import static ru.lunchvoting.user.RestaurantTestData.MCDONALDS;
import static ru.lunchvoting.user.UserTestData.*;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "user", "dish");

    public static final int USER_VOTE_ID = 1;
    public static final int ADMIN_VOTE_ID = 2;
    public static final int GUEST_VOTE_ID = 3;

    public static final Vote USER_VOTE = new Vote(USER_VOTE_ID, USER, KFC, LocalDateTime.of(2024, 1, 1, 12, 0, 0));
    public static final Vote ADMIN_VOTE = new Vote(ADMIN_VOTE_ID, ADMIN, MCDONALDS, LocalDateTime.of(2024, 1, 1, 12, 0, 0));
    public static final Vote GUEST_VOTE = new Vote(GUEST_VOTE_ID, GUEST, MCDONALDS, LocalDateTime.of(2024, 1, 1, 12, 0, 0));
}
