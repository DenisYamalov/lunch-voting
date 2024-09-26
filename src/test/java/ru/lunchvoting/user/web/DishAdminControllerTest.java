package ru.lunchvoting.user.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.lunchvoting.AbstractControllerTest;
import ru.lunchvoting.common.util.JsonUtil;
import ru.lunchvoting.user.DishTestData;
import ru.lunchvoting.user.model.Dish;
import ru.lunchvoting.user.repository.DishRepository;
import ru.lunchvoting.user.to.DishTo;
import ru.lunchvoting.user.util.DishUtil;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.user.DishTestData.*;
import static ru.lunchvoting.user.RestaurantTestData.KFC;
import static ru.lunchvoting.user.RestaurantTestData.KFC_ID;
import static ru.lunchvoting.user.UserTestData.ADMIN_MAIL;
import static ru.lunchvoting.user.UserTestData.USER_MAIL;
import static ru.lunchvoting.user.web.RestaurantAdminController.RESTAURANT_ADMIN_URL;

@Slf4j
class DishAdminControllerTest extends AbstractControllerTest {

    private static final String KFC_URL = RESTAURANT_ADMIN_URL + "/" + KFC_ID + "/dishes";
    private static final String KFC_URL_SLASH = KFC_URL + "/";

    @Autowired
    DishRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(KFC_URL_SLASH + HAMBURGER_ID))
                .andExpect(status().isNoContent());
        assertFalse(repository.get(KFC_ID, HAMBURGER_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.delete(KFC_URL_SLASH + HAMBURGER_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        DishTo newDishTo = DishTestData.getNewTo();
        ResultActions action = perform(MockMvcRequestBuilders.post(KFC_URL)
                                               .contentType(MediaType.APPLICATION_JSON)
                                               .content(JsonUtil.writeValue(newDishTo)))
                .andDo(print());
        Dish created = DISH_MATCHER.readFromJson(action);
        int newId = created.id();
        Dish newDish = DishUtil.createNewFromTo(newDishTo, KFC);
        newDish.setId(newId);
        DISH_NO_DATE_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(repository.getExisted(newId), newDish);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = new Dish(null, null, null, null);
        perform(MockMvcRequestBuilders.post(KFC_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        DishTo duplicate = new DishTo(null, HAMBURGER_NAME, LocalDate.now(), 100L);
        perform(MockMvcRequestBuilders.post(KFC_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        DishTo updated = DishTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(KFC_URL_SLASH + HAMBURGER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        Dish newFromTo = DishUtil.createNewFromTo(updated, KFC);
        DISH_MATCHER.assertMatch(repository.getExisted(HAMBURGER_ID), newFromTo);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Dish invalid = new Dish(HAMBURGER_ID, null, null, null);
        perform(MockMvcRequestBuilders.put(KFC_URL_SLASH + HAMBURGER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        DishTo invalid = new DishTo(HAMBURGER_ID, CHEESEBURGER_NAME, LocalDate.now(), 150L);
        perform(MockMvcRequestBuilders.put(KFC_URL_SLASH + HAMBURGER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Dish invalid = new Dish(HAMBURGER_ID, "<script>alert(123)</script>", KFC, 150L);
        perform(MockMvcRequestBuilders.put(KFC_URL_SLASH + HAMBURGER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}