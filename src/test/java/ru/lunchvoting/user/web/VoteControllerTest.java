package ru.lunchvoting.user.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.lunchvoting.AbstractControllerTest;
import ru.lunchvoting.user.repository.VoteRepository;

import java.time.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.lunchvoting.user.RestaurantTestData.BURGERKING_ID;
import static ru.lunchvoting.user.RestaurantTestData.KFC_ID;
import static ru.lunchvoting.user.UserTestData.USER_ID;
import static ru.lunchvoting.user.UserTestData.USER_MAIL;
import static ru.lunchvoting.user.VoteTestData.*;
import static ru.lunchvoting.user.web.VoteController.VOTE_URL;

class VoteControllerTest extends AbstractControllerTest {
    private static final String VOTE_URL_SLASH = VOTE_URL + "/";

    @MockBean
    Clock clock;

    @Autowired
    VoteRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVote() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> assertEquals(KFC_ID, Integer.parseInt(result.getResponse().getContentAsString())));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVoteResults() throws Exception {
        perform(MockMvcRequestBuilders.get(VOTE_URL + "/results")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(VOTE_RESULT_MATCHER.contentJson(List.of(VOTE_MCDONALDS_RESULT, VOTE_KFC_RESULT)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        Clock fixedClock =
                Clock.fixed(Instant.parse(LocalDate.now().atTime(10, 0).toInstant(ZoneOffset.of("Z")).toString()),
                            ZoneId.of("UTC"));
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        perform(MockMvcRequestBuilders.post(VOTE_URL_SLASH + KFC_ID))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateVote() throws Exception {
        Clock fixedClock =
                Clock.fixed(Instant.parse(LocalDate.now().atTime(10, 55).toInstant(ZoneOffset.of("Z")).toString()),
                            ZoneId.of("UTC"));
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        perform(MockMvcRequestBuilders.put(VOTE_URL_SLASH + BURGERKING_ID))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(BURGERKING_ID, repository.getExisted(USER_ID).getRestaurant().id());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateVoteLate() throws Exception {
        Clock fixedClock =
                Clock.fixed(Instant.parse(LocalDate.now().atTime(12, 0).toInstant(ZoneOffset.of("Z")).toString()),
                            ZoneId.of("UTC"));
        when(clock.instant()).thenReturn(fixedClock.instant());
        when(clock.getZone()).thenReturn(fixedClock.getZone());
        perform(MockMvcRequestBuilders.put(VOTE_URL_SLASH + BURGERKING_ID))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        assertNotEquals(BURGERKING_ID, repository.getExisted(USER_ID).getRestaurant().id());
    }
}