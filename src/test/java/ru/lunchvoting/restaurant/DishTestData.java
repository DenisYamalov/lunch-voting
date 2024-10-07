package ru.lunchvoting.restaurant;

import ru.lunchvoting.MatcherFactory;
import ru.lunchvoting.restaurant.model.Dish;
import ru.lunchvoting.restaurant.to.DishTo;

import java.time.LocalDate;

import static ru.lunchvoting.restaurant.RestaurantTestData.*;

public class DishTestData {

    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final MatcherFactory.Matcher<Dish> DISH_NO_DATE_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant", "menuDate");

    public static final int HAMBURGER_ID = 1;
    public static final int CHEESEBURGER_ID = 2;
    public static final int BIGMAC_ID = 3;
    public static final int MCCHICKEN_ID = 4;
    public static final int WHOPPER_ID = 5;
    public static final int BACONKING_ID = 6;
    public static final int HAMBURGER_ID2 = 7;
    public static final int CHEESEBURGER_ID2 = 8;
    public static final int HAMBURGER_ID3 = 9;
    public static final int CHEESEBURGER_ID3 = 10;

    public static final String HAMBURGER_NAME = "Hamburger";
    public static final String CHEESEBURGER_NAME = "Cheeseburger";
    public static final String BIGMAC_NAME = "Big Mac";
    public static final String MCCHICKEN_NAME = "McChicken";
    public static final String WHOPPER_NAME = "Whopper";
    public static final String BACONKING_NAME = "Bacon King";

    public static final Dish HAMBURGER_TODAY = new Dish(HAMBURGER_ID, HAMBURGER_NAME, LocalDate.now(), 100L, KFC);
    public static final Dish CHEESEBURGER_TODAY = new Dish(CHEESEBURGER_ID, CHEESEBURGER_NAME, LocalDate.now(), 150L, KFC);

    public static final Dish HAMBURGER_OCT01 = new Dish(HAMBURGER_ID2, HAMBURGER_NAME, LocalDate.of(2024, 10, 1), 100L, KFC);
    public static final Dish CHEESEBURGER_OCT01 = new Dish(CHEESEBURGER_ID2, CHEESEBURGER_NAME, LocalDate.of(2024, 10, 1), 150L, KFC);

    public static final Dish HAMBURGER_OCT02 = new Dish(HAMBURGER_ID3, HAMBURGER_NAME, LocalDate.of(2024, 10, 2), 100L, KFC);
    public static final Dish CHEESEBURGER_OCT02 = new Dish(CHEESEBURGER_ID3, CHEESEBURGER_NAME, LocalDate.of(2024, 10, 2), 150L, KFC);

    public static final Dish BIGMAC = new Dish(BIGMAC_ID, BIGMAC_NAME, LocalDate.now(), 200L, MCDONALDS);
    public static final Dish MCCHICKEN = new Dish(MCCHICKEN_ID, MCCHICKEN_NAME, LocalDate.now(), 100L, MCDONALDS);
    public static final Dish WHOPPER = new Dish(WHOPPER_ID, WHOPPER_NAME, LocalDate.now(), 150L, BURGERKING);
    public static final Dish BACONKING = new Dish(BACONKING_ID, BACONKING_NAME, LocalDate.now(), 200L, BURGERKING);

    public static DishTo getNewTo() {
        return new DishTo(null, "New dish", LocalDate.now(), 100L);
    }

    public static DishTo getUpdated() {
        return new DishTo(HAMBURGER_ID, "Updated Hamburger", LocalDate.now(), 123L);
    }
}
