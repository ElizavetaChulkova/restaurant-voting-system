package ru.chulkova.restaurantvoting.web.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.chulkova.restaurantvoting.config.WebSecurityConfig;
import ru.chulkova.restaurantvoting.model.Meal;
import ru.chulkova.restaurantvoting.model.Restaurant;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;
import ru.chulkova.restaurantvoting.util.JsonUtil;
import ru.chulkova.restaurantvoting.web.AbstractControllerTest;
import ru.chulkova.restaurantvoting.web.UserTestUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.chulkova.restaurantvoting.util.JsonUtil.writeValue;
import static ru.chulkova.restaurantvoting.web.UserTestUtil.*;
import static ru.chulkova.restaurantvoting.web.UserTestUtil.MEAL_ID;
import static ru.chulkova.restaurantvoting.web.admin.AdminMealController.ADMIN_MEAL_URL;
import static ru.chulkova.restaurantvoting.web.admin.AdminRestaurantController.ADMIN_REST_URL;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    @Autowired
    private RestaurantRepository repository;

    @Test
    @WithUserDetails(value = UserTestUtil.ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_REST_URL + "/" + REST_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(value = UserTestUtil.ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_REST_URL))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails(value = UserTestUtil.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL + "/" + REST_ID))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(repository.findById(REST_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRest = new Restaurant("new rest", null);
        ResultActions result = perform(MockMvcRequestBuilders.post(ADMIN_REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newRest)))
                .andExpect(status().isCreated());
        Restaurant created = JsonUtil.readValue(result.andReturn().getResponse().getContentAsString(),
                Restaurant.class);
        Integer newId = created.id();
        UserTestUtil.assertNoIdEquals(newRest, repository.findById(newId).orElseThrow());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updatedRest = new Restaurant(REST_ID, "updated rest", null);
        perform(MockMvcRequestBuilders.put(ADMIN_MEAL_URL + "/" + REST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updatedRest)))
                .andDo(print())
                .andExpect(status().isNoContent());
        UserTestUtil.assertEquals(updatedRest, repository.findById(REST_ID).orElseThrow());
    }
}