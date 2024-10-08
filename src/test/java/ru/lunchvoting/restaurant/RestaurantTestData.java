package ru.lunchvoting.restaurant;

import ru.lunchvoting.MatcherFactory;
import ru.lunchvoting.restaurant.model.Restaurant;
import ru.lunchvoting.restaurant.to.RestaurantWithDishesTo;
import ru.lunchvoting.restaurant.util.RestaurantUtil;

import java.util.List;

import static ru.lunchvoting.restaurant.DishTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");
    public static final MatcherFactory.Matcher<RestaurantWithDishesTo> RESTAURANT_WITH_DISHES_TO_MATCHER =
            MatcherFactory.usingEqualsComparator(RestaurantWithDishesTo.class);

    public static final int KFC_ID = 1;
    public static final int MCDONALDS_ID = 2;
    public static final int BURGERKING_ID = 3;

    public static final String KFC_NAME = "KFC";
    public static final String MCDONALDS_NAME = "McDonalds";
    public static final String BURGERKING_NAME = "Burger King";

    public static final Restaurant KFC = new Restaurant(KFC_ID, KFC_NAME);
    public static final Restaurant MCDONALDS = new Restaurant(MCDONALDS_ID, MCDONALDS_NAME);
    public static final Restaurant BURGERKING = new Restaurant(BURGERKING_ID, BURGERKING_NAME);

    public static List<RestaurantWithDishesTo> getWithDishes() {
        KFC.setMenu(List.of(HAMBURGER_TODAY, CHEESEBURGER_TODAY));
        MCDONALDS.setMenu(List.of(BIGMAC, MCCHICKEN));
        BURGERKING.setMenu(List.of(WHOPPER, BACONKING));
        return RestaurantUtil.toWithDishes(List.of(KFC, MCDONALDS, BURGERKING));
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(MCDONALDS_ID, "Updated McDonald's");
    }
}
