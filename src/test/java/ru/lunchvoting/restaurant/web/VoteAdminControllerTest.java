package ru.lunchvoting.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lunchvoting.AbstractControllerTest;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.restaurant.web.VoteAdminController.VOTE_ADMIN_URL;
import static ru.lunchvoting.user.UserTestData.ADMIN_MAIL;
import static ru.lunchvoting.user.UserTestData.USER_MAIL;
import static ru.lunchvoting.restaurant.VoteTestData.*;

class VoteAdminControllerTest extends AbstractControllerTest {

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getVoteResultsByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_ADMIN_URL + "/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(VOTE_RESULT_MATCHER.contentJson(List.of(VOTE_MCDONALDS_RESULT, VOTE_KFC_RESULT)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getVoteResultsByDateWithoutDate() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_ADMIN_URL + "/results")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(VOTE_RESULT_MATCHER.contentJson(List.of(VOTE_MCDONALDS_RESULT, VOTE_KFC_RESULT)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVoteResultsByDateNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_ADMIN_URL + "/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}