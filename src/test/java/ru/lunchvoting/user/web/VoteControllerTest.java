package ru.lunchvoting.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lunchvoting.AbstractControllerTest;

import java.time.*;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.user.RestaurantTestData.KFC_ID;
import static ru.lunchvoting.user.UserTestData.USER_MAIL;
import static ru.lunchvoting.user.VoteTestData.*;
import static ru.lunchvoting.user.web.VoteController.VOTE_URL;

class VoteControllerTest extends AbstractControllerTest {
    private static final String VOTE_URL_SLASH = VOTE_URL + "/";

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVoteResults() throws Exception {
        ResultActions actions = perform(MockMvcRequestBuilders.get(VOTE_URL)
                                                .contentType(MediaType.APPLICATION_JSON)
                                                .param("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(VOTE_RESULT_MATCHER.contentJson(List.of(VOTE_KFC_RESULT, VOTE_MCDONALDS_RESULT)));

    }

    //TODO mock dateTime or refactor and set it
    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        Clock clock =
                Clock.fixed(Instant.parse(LocalDate.now().atTime(10, 0).toInstant(ZoneOffset.of("Z")).toString()),
                            ZoneId.of("UTC"));
        perform(MockMvcRequestBuilders.post(VOTE_URL_SLASH + KFC_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }
}