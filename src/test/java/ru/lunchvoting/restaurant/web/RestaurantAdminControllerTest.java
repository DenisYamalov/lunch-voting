package ru.lunchvoting.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.AbstractControllerTest;
import ru.lunchvoting.restaurant.model.Restaurant;
import ru.lunchvoting.restaurant.repository.RestaurantRepository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.restaurant.RestaurantTestData.*;
import static ru.lunchvoting.restaurant.web.RestaurantAdminController.RESTAURANT_ADMIN_URL;
import static ru.lunchvoting.user.UserTestData.ADMIN_MAIL;
import static ru.lunchvoting.user.UserTestData.USER_MAIL;

class RestaurantAdminControllerTest extends AbstractControllerTest {

    private static final String RESTAURANT_ADMIN_URL_SLASH = RESTAURANT_ADMIN_URL + "/";

    @Autowired
    RestaurantRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANT_ADMIN_URL_SLASH + KFC_ID))
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(KFC_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.delete(RESTAURANT_ADMIN_URL_SLASH + KFC_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = getResultActionsPost(newRestaurant, RESTAURANT_ADMIN_URL);
        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.getExisted(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(null, null);
        getResultActionsPost(invalid, RESTAURANT_ADMIN_URL)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Restaurant invalid = new Restaurant(null, KFC_NAME);
        getResultActionsPost(invalid, RESTAURANT_ADMIN_URL)
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = getUpdated();
        getResultActionsPut(updated, RESTAURANT_ADMIN_URL_SLASH + MCDONALDS_ID)
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(repository.getExisted(MCDONALDS_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(KFC_ID, null);
        getResultActionsPut(invalid, RESTAURANT_ADMIN_URL_SLASH + KFC_ID)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        Restaurant invalid = new Restaurant(MCDONALDS_ID, KFC_NAME);
        getResultActionsPut(invalid, RESTAURANT_ADMIN_URL_SLASH + MCDONALDS_ID)
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Restaurant invalid = new Restaurant(MCDONALDS_ID, "<script>alert(123)</script>");
        getResultActionsPost(invalid, RESTAURANT_ADMIN_URL_SLASH + MCDONALDS_ID)
                .andExpect(status().isUnprocessableEntity());
    }
}