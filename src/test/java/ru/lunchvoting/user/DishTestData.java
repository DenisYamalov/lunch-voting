package ru.lunchvoting.user;

import ru.lunchvoting.MatcherFactory;
import ru.lunchvoting.user.model.Dish;
import ru.lunchvoting.user.to.DishTo;

import java.time.LocalDate;

import static ru.lunchvoting.user.RestaurantTestData.*;

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

    public static final String HAMBURGER_NAME = "Hamburger";
    public static final String CHEESEBURGER_NAME = "Cheeseburger";
    public static final String BIGMAC_NAME = "Big Mac";
    public static final String MCCHICKEN_NAME = "McChicken";
    public static final String WHOPPER_NAME = "Whopper";
    public static final String BACONKING_NAME = "Bacon King";

    public static final Dish HAMBURGER = new Dish(HAMBURGER_ID, HAMBURGER_NAME, KFC, 100L);
    public static final Dish CHEESEBURGER = new Dish(CHEESEBURGER_ID, CHEESEBURGER_NAME, KFC, 150L);
    public static final Dish BIGMAC = new Dish(BIGMAC_ID, BIGMAC_NAME, MCDONALDS, 200L);
    public static final Dish MCCHICKEN = new Dish(MCCHICKEN_ID, MCCHICKEN_NAME, MCDONALDS, 100L);
    public static final Dish WHOPPER = new Dish(WHOPPER_ID, WHOPPER_NAME, BURGERKING, 150L);
    public static final Dish BACONKING = new Dish(BACONKING_ID, BACONKING_NAME, BURGERKING, 200L);

    public static Dish getNew() {
        return new Dish(null, "New dish", KFC, 100L);
    }

    public static DishTo getNewTo() {
        return new DishTo(null, "New dish", LocalDate.now(), 100L);
    }

    public static DishTo getUpdated() {
        return new DishTo(HAMBURGER_ID, "Updated Hamburger", LocalDate.now(), 123L);
    }
}
