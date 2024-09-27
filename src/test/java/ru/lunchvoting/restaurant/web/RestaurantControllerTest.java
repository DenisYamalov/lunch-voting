package ru.lunchvoting.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithUserDetails;
import ru.lunchvoting.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.restaurant.RestaurantTestData.*;
import static ru.lunchvoting.restaurant.web.RestaurantController.RESTAURANT_URL;
import static ru.lunchvoting.user.UserTestData.USER_MAIL;

class RestaurantControllerTest extends AbstractControllerTest {

    private static final String RESTAURANT_URL_SLASH = RESTAURANT_URL + "/";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        getResultActionsGet(RESTAURANT_URL_SLASH + KFC_ID)
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_MATCHER.contentJson(KFC));
    }

    @Test
    void getUnauth() throws Exception {
        getResultActionsGet(RESTAURANT_URL_SLASH + KFC_ID)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        getResultActionsGet(RESTAURANT_URL_SLASH + KFC_ID + 10)
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        getResultActionsGet(RESTAURANT_URL)
                .andExpect(status().isOk())
                .andExpect(RESTAURANT_MATCHER.contentJson(KFC, MCDONALDS, BURGERKING));
    }
}