package ru.chulkova.restaurantvoting.web.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.chulkova.restaurantvoting.repository.MealRepository;
import ru.chulkova.restaurantvoting.web.AbstractControllerTest;
import ru.chulkova.restaurantvoting.web.UserTestUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.chulkova.restaurantvoting.web.UserTestUtil.MEAL_ID;
import static ru.chulkova.restaurantvoting.web.admin.AdminMealController.ADMIN_MEAL_URL;

class AdminMealControllerTest extends AbstractControllerTest {

    @Autowired
    private MealRepository mealRepository;

    @Test
    @WithUserDetails(value = UserTestUtil.ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_MEAL_URL + "/meals"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }

    @Test
    @WithUserDetails(value = UserTestUtil.ADMIN_MAIL)
    void getAllByRestaurantId() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_MEAL_URL + "/" + MEAL_ID + "/meals"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithUserDetails(value = UserTestUtil.ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(ADMIN_MEAL_URL + "/meals/" + MEAL_ID))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(mealRepository.findById(MEAL_ID).isPresent());
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