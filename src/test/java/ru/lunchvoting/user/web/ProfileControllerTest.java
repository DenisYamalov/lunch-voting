package ru.lunchvoting.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lunchvoting.AbstractControllerTest;
import ru.lunchvoting.user.model.User;
import ru.lunchvoting.user.repository.UserRepository;
import ru.lunchvoting.user.to.UserTo;
import ru.lunchvoting.user.util.UsersUtil;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.user.UserTestData.*;

public class ProfileControllerTest extends AbstractControllerTest {

    public static final String REST_URL = ProfileController.REST_URL;

    @Autowired
    private UserRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        getResultActionsGet(REST_URL)
                .andExpect(status().isOk())
                .andExpect(USER_MATCHER.contentJson(USER));
    }

    @Test
    void getUnAuth() throws Exception {
        getResultActionsGet(REST_URL)
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL))
                .andExpect(status().isNoContent());
        USER_MATCHER.assertMatch(repository.findAll(), ADMIN, SECOND_USER);
    }

    @Test
    void register() throws Exception {
        UserTo newTo = getNewTo();
        User newUser = UsersUtil.createNewFromTo(newTo);
        ResultActions action = getResultActionsPost(newTo, REST_URL)
                .andExpect(status().isCreated());

        User created = USER_MATCHER.readFromJson(action);
        newUser.setId(created.id());
        int newId = created.id();
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(repository.getExisted(newId), newUser);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", USER_MAIL, "newPassword");
        getResultActionsPut(updatedTo, REST_URL)
                .andExpect(status().isNoContent());

        USER_MATCHER.assertMatch(repository.getExisted(USER_ID), UsersUtil.updateFromTo(new User(USER), updatedTo));
    }

    @Test
    void registerInvalid() throws Exception {
        UserTo newTo = new UserTo(null, null, null, null);
        getResultActionsPost(newTo, REST_URL)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInvalid() throws Exception {
        UserTo updatedTo = new UserTo(null, null, "password", null);
        getResultActionsPut(updatedTo, REST_URL)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateDuplicate() throws Exception {
        UserTo updatedTo = new UserTo(null, "newName", ADMIN_MAIL, "newPassword");
        getResultActionsPut(updatedTo, REST_URL)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string(containsString(UniqueMailValidator.EXCEPTION_DUPLICATE_EMAIL)));
    }
}