package ru.chulkova.restaurantvoting.web.admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.chulkova.restaurantvoting.model.Meal;
import ru.chulkova.restaurantvoting.repository.MealRepository;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;
import ru.chulkova.restaurantvoting.util.JsonUtil;
import ru.chulkova.restaurantvoting.web.AbstractControllerTest;
import ru.chulkova.restaurantvoting.web.TestUtil;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.chulkova.restaurantvoting.util.JsonUtil.writeValue;
import static ru.chulkova.restaurantvoting.web.TestUtil.*;
import static ru.chulkova.restaurantvoting.web.admin.AdminMealController.ADMIN_MEAL_URL;

class AdminMealControllerTest extends AbstractControllerTest {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getAll() throws Exception {
//        perform(MockMvcRequestBuilders.get(ADMIN_MEAL_URL + "/meals"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//
//    }

//    @Test
//    @WithUserDetails(value = ADMIN_MAIL)
//    void getAllByRestaurantId() throws Exception {
//        perform(MockMvcRequestBuilders.get(ADMIN_MEAL_URL + "/" + REST_ID + "/meals"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
//    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_MEAL_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders
                .delete(ADMIN_MEAL_URL + "/" + REST_ID + "/meals/" + MEAL_ID))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(mealRepository.findById(MEAL_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Meal newMeal = new Meal(null, 222, "new meal",
                LocalDate.of(2023, 2, 10), restaurantRepository.getById(REST_ID));
        ResultActions result = perform(MockMvcRequestBuilders
                .post(ADMIN_MEAL_URL + "/" + REST_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newMeal)))
                .andExpect(status().isCreated());
        Meal created = JsonUtil.readValue(result.andReturn().getResponse().getContentAsString(), Meal.class);
        Integer newMealId = created.id();
        TestUtil.assertNoIdEquals(newMeal, mealRepository.findById(newMealId).orElseThrow());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Meal oldMeal = mealRepository.findById(MEAL_ID).orElse(null);
        Meal updated = new Meal(MEAL_ID, 200, "updated meal",
                LocalDate.of(2023, 1, 11), oldMeal.getRestaurant());
        perform(MockMvcRequestBuilders.put(ADMIN_MEAL_URL + "/" + REST_ID + "/meals/" + MEAL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        TestUtil.assertEquals(updated, mealRepository.findById(MEAL_ID).orElseThrow());
    }
}