package ru.lunchvoting.user;

import ru.lunchvoting.MatcherFactory;
import ru.lunchvoting.user.model.Restaurant;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");

    public static final int KFC_ID = 1;
    public static final int MCDONALDS_ID = 2;
    public static final int BURGERKING_ID = 3;

    public static final String KFC_NAME = "KFC";
    public static final String MCDONALDS_NAME = "McDonalds";
    public static final String BURGERKING_NAME = "Burger King";

    public static final Restaurant KFC = new Restaurant(KFC_ID, KFC_NAME);
    public static final Restaurant MCDONALDS = new Restaurant(MCDONALDS_ID, MCDONALDS_NAME);
    public static final Restaurant BURGERKING = new Restaurant(BURGERKING_ID, BURGERKING_NAME);

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(MCDONALDS_ID, "Updated McDonald's");
    }
}
