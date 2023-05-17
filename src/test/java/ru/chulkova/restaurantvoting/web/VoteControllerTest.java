package ru.chulkova.restaurantvoting.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.chulkova.restaurantvoting.model.Vote;
import ru.chulkova.restaurantvoting.repository.VoteRepository;
import ru.chulkova.restaurantvoting.util.JsonUtil;

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
        ResultActions result = perform(MockMvcRequestBuilders.post("/api/account/vote/" + 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newVote)))
                .andExpect(status().isCreated());
        Vote created = JsonUtil.readValue(result.andReturn().getResponse().getContentAsString(), Vote.class);
        Integer newId = created.getId();
        TestUtil.assertNoIdEquals(newVote, repository.findById(newId).orElseThrow());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateVote() throws Exception {
        Vote updated = new Vote(VOTE_ID, LocalDate.now(),
                LocalTime.now().truncatedTo(ChronoUnit.MINUTES), admin, restaurant);
        try {
            perform(MockMvcRequestBuilders.put("/api/account/vote/" + 2)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(writeValue(updated)))
                    .andExpect(status().isNoContent());
            TestUtil.assertEquals(updated, repository.findById(VOTE_ID).orElseThrow());
        } catch (Exception e) {
            Assertions.assertEquals(e.getCause().getMessage(), "You are not allowed to change your vote");
        }
    }

    @Test
    @WithUserDetails(value = TestUtil.USER_MAIL)
    void getAllUserVotes() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/account/vote/all-votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}