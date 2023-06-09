package ru.chulkova.restaurantvoting.web.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.chulkova.restaurantvoting.model.Vote;
import ru.chulkova.restaurantvoting.repository.VoteRepository;
import ru.chulkova.restaurantvoting.service.VoteService;
import ru.chulkova.restaurantvoting.util.JsonUtil;
import ru.chulkova.restaurantvoting.web.AbstractControllerTest;
import ru.chulkova.restaurantvoting.web.TestUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.chulkova.restaurantvoting.util.JsonUtil.writeValue;
import static ru.chulkova.restaurantvoting.web.TestUtil.*;

class VoteControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteRepository repository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void create() throws Exception {
        Vote newVote = new Vote(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                user, restaurant);
        ResultActions result = perform(MockMvcRequestBuilders
                .post("/api/account/vote/" + restaurant.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newVote)))
                .andExpect(status().isCreated());
        Vote created = JsonUtil.readValue(result.andReturn().getResponse().getContentAsString(), Vote.class);
        Integer newId = created.getId();
        TestUtil.assertNoIdEquals(newVote, repository.findById(newId).orElseThrow());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Vote updated = new Vote(VOTE_ID, LocalDate.now(),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES), admin, restaurant);
        if (LocalTime.now().isBefore(VoteService.NO_CHANGE_TIME)) {
            perform(MockMvcRequestBuilders.put("/api/account/vote/" + restaurant.id())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(writeValue(updated)))
                    .andExpect(status().isNoContent());
            TestUtil.assertEquals(updated, repository.findById(VOTE_ID).orElseThrow());
        } else {
            ResultActions resultActions = perform(MockMvcRequestBuilders
                    .put("/api/account/vote/" + restaurant.id())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(writeValue(updated)))
                    .andExpect(status().isForbidden());
            Assertions.assertEquals(resultActions.andReturn().getResolvedException().getMessage(),
                    VoteService.EXCEPTION_MESSAGE);
        }
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateVoteBeforeDeadline() throws Exception {
        Assumptions.assumeTrue(LocalTime.now().isBefore(LocalTime.of(11, 0)));
        Vote updated = new Vote(VOTE_ID, LocalDate.now(),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES), admin, restaurant);
        perform(MockMvcRequestBuilders.put("/api/account/vote/" + restaurant.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andExpect(status().isNoContent());
        TestUtil.assertEquals(updated, repository.findById(VOTE_ID).orElseThrow());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateVoteAfterDeadline() throws Exception {
        Assumptions.assumeTrue(LocalTime.now().isAfter(LocalTime.of(11, 0)));
        Vote updated = new Vote(VOTE_ID, LocalDate.now(),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES), admin, restaurant);
        ResultActions resultActions = perform(MockMvcRequestBuilders
                .put("/api/account/vote/" + restaurant.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andExpect(status().isForbidden());
        Assertions.assertEquals(resultActions.andReturn().getResolvedException().getMessage(),
                VoteService.EXCEPTION_MESSAGE);
    }

    @Test
    @WithUserDetails(value = TestUtil.USER_MAIL)
    void getAllUserVotes() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/account/vote/all-votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(value = TestUtil.USER_MAIL)
    void getByDate() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/account/vote/by-date")
                .param("date", "2023-02-10"))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}