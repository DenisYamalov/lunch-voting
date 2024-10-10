package ru.lunchvoting.vote.web;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lunchvoting.AbstractControllerTest;
import ru.lunchvoting.app.AuthUser;
import ru.lunchvoting.common.util.JsonUtil;
import ru.lunchvoting.vote.model.Vote;
import ru.lunchvoting.vote.repository.VoteRepository;
import ru.lunchvoting.user.model.User;
import ru.lunchvoting.user.to.UserTo;
import ru.lunchvoting.user.util.UsersUtil;
import ru.lunchvoting.user.web.ProfileControllerTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.restaurant.RestaurantTestData.BURGERKING_ID;
import static ru.lunchvoting.vote.VoteTestData.*;
import static ru.lunchvoting.vote.web.VoteController.VOTE_URL;
import static ru.lunchvoting.user.UserTestData.*;

class VoteControllerTest extends AbstractControllerTest {
    private static final String VOTE_URL_SLASH = VOTE_URL + "/";

    @Autowired
    VoteRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVoteByDateToday() throws Exception {
        getResultActionsGet(VOTE_URL_SLASH + "by-date")
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(USER_VOTE_TO));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVoteById() throws Exception {
        getResultActionsGet(VOTE_URL_SLASH + USER_VOTE_ID)
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(USER_VOTE_TO));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVotes() throws Exception {
        getResultActionsGet(VOTE_URL)
                .andExpect(status().isOk())
                .andExpect(VOTE_TO_MATCHER.contentJson(List.of(USER_VOTE_TO)));
    }

    @Test
    void vote() throws Exception {
        UserTo newTo = getNewTo();
        User newUser = UsersUtil.createNewFromTo(newTo);
        ResultActions action = getResultActionsPost(newTo, ProfileControllerTest.REST_URL);
        User created = USER_MATCHER.readFromJson(action);
        newUser.setId(created.getId());

        perform(MockMvcRequestBuilders.post(VOTE_URL)
                        .with(user(new AuthUser(newUser)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(BURGERKING_ID)))
                .andDo(print())
                .andExpect(status().isCreated());

        Vote existed = repository.findByUserIdAndVoteDate(newUser.getId(), LocalDate.now()).get();
        assertEquals(BURGERKING_ID, existed.getRestaurant().id());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteAgain() throws Exception {
        getResultActionsPost(BURGERKING_ID, VOTE_URL)
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateVote() throws Exception {
        //https://stackoverflow.com/a/76663689
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class,
                                                                           Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime currentDateTime = LocalDate.now().atTime(10, 0);
            mockedStatic.when(LocalDateTime::now).thenReturn(currentDateTime);

            getResultActionsPut(BURGERKING_ID, VOTE_URL_SLASH + USER_ID)
                    .andExpect(status().isNoContent());

            assertEquals(BURGERKING_ID, repository.getExisted(USER_ID).getRestaurant().id());
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateVoteLate() throws Exception {
        //https://stackoverflow.com/a/76663689
        try (MockedStatic<LocalDateTime> mockedStatic = Mockito.mockStatic(LocalDateTime.class,
                                                                           Mockito.CALLS_REAL_METHODS)) {
            LocalDateTime currentDateTime = LocalDate.now().atTime(12, 0);
            mockedStatic.when(LocalDateTime::now).thenReturn(currentDateTime);

            getResultActionsPut(BURGERKING_ID, VOTE_URL_SLASH + USER_ID)
                    .andExpect(status().isUnprocessableEntity());
            assertNotEquals(BURGERKING_ID, repository.getExisted(USER_ID).getRestaurant().id());
        }
    }
}