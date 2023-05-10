package ru.chulkova.restaurantvoting.web.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;
import ru.chulkova.restaurantvoting.web.AbstractControllerTest;
import ru.chulkova.restaurantvoting.web.UserTestUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.chulkova.restaurantvoting.web.UserTestUtil.REST_ID;
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
    @WithUserDetails(value = UserTestUtil.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_REST_URL + "/" + REST_ID))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(repository.findById(REST_ID).isPresent());
    }

    //TODO test create
    @Test
    void create() {
    }

    //TODO test update
    @Test
    void update() {
    }
}