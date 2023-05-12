package ru.chulkova.restaurantvoting.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.chulkova.restaurantvoting.model.Role;
import ru.chulkova.restaurantvoting.model.User;
import ru.chulkova.restaurantvoting.repository.UserRepository;
import ru.chulkova.restaurantvoting.util.JsonUtil;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.chulkova.restaurantvoting.util.JsonUtil.writeValue;

class RootControllerTest extends AbstractControllerTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void register() throws Exception {
        User newUser = new User("newemail@gmail.com", "New Name",
                "password", Set.of(Role.USER));
        ResultActions result = perform(MockMvcRequestBuilders.post("/api/root/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newUser)))
                .andExpect(status().isCreated());
        User created = JsonUtil.readValue(result.andReturn().getResponse().getContentAsString(), User.class);
        Integer newId = created.id();
        UserTestUtil.assertNoIdEquals(newUser, userRepository.findById(newId).orElseThrow());
    }

    @Test
    void getRestaurantsWithMeals() throws Exception {
        perform(MockMvcRequestBuilders.get("/api/root/restaurants"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}