package ru.lunchvoting.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lunchvoting.AbstractControllerTest;
import ru.lunchvoting.user.repository.DishRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.user.DishTestData.*;
import static ru.lunchvoting.user.RestaurantTestData.*;
import static ru.lunchvoting.user.UserTestData.USER_MAIL;
import static ru.lunchvoting.user.web.RestaurantController.RESTAURANT_URL;

public class DishControllerTest extends AbstractControllerTest {

    private static final String KFC_DISH_URL = RESTAURANT_URL + "/" + KFC_ID + "/dishes";
    private static final String KFC_DISH_URL_SLASH = KFC_DISH_URL + "/";

    @Autowired
    DishRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(KFC_DISH_URL_SLASH + HAMBURGER_ID))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(HAMBURGER));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(KFC_DISH_URL_SLASH + HAMBURGER_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(KFC_DISH_URL_SLASH + HAMBURGER_ID + 10))
                .andDo(print())
                .andExpect(status().isConflict());
    }


    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(KFC_DISH_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(HAMBURGER,CHEESEBURGER));
    }
}
