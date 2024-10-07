package ru.lunchvoting.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.lunchvoting.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.restaurant.DishTestData.*;
import static ru.lunchvoting.restaurant.RestaurantTestData.KFC_ID;
import static ru.lunchvoting.restaurant.web.RestaurantController.RESTAURANT_URL;
import static ru.lunchvoting.user.UserTestData.USER_MAIL;

public class DishControllerTest extends AbstractControllerTest {

    private static final String KFC_DISH_URL = RESTAURANT_URL + "/" + KFC_ID + "/dishes";
    private static final String KFC_DISH_URL_SLASH = KFC_DISH_URL + "/";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        getResultActionsGet(KFC_DISH_URL_SLASH + HAMBURGER_ID)
                .andExpect(status().isOk())
                .andExpect(DISH_MATCHER.contentJson(HAMBURGER_TODAY));
    }

    @Test
    void getUnauth() throws Exception {
        getResultActionsGet(KFC_DISH_URL_SLASH + HAMBURGER_ID)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        getResultActionsGet(KFC_DISH_URL_SLASH + HAMBURGER_ID + 10)
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        getResultActionsGet(KFC_DISH_URL)
                .andExpect(status().isOk())
                .andExpect(DISH_MATCHER.contentJson(CHEESEBURGER_TODAY, HAMBURGER_TODAY, CHEESEBURGER_OCT02, HAMBURGER_OCT02, CHEESEBURGER_OCT01, HAMBURGER_OCT01));
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllByDate() throws Exception {
        getResultActionsGet(KFC_DISH_URL_SLASH+"by-date?date=2024-10-01")
                .andExpect(status().isOk())
                .andExpect(DISH_MATCHER.contentJson(CHEESEBURGER_OCT01, HAMBURGER_OCT01));
    }
}
