package ru.lunchvoting.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lunchvoting.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.restaurant.RestaurantTestData.*;
import static ru.lunchvoting.user.UserTestData.USER_MAIL;
import static ru.lunchvoting.restaurant.web.RestaurantController.RESTAURANT_URL;

class RestaurantControllerTest extends AbstractControllerTest {

    private static final String RESTAURANT_URL_SLASH = RESTAURANT_URL + "/";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL_SLASH + KFC_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(KFC));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL_SLASH + KFC_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL_SLASH + KFC_ID + 10))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(RESTAURANT_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(KFC, MCDONALDS, BURGERKING));
    }
}