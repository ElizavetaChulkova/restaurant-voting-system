package ru.chulkova.restaurantvoting.web.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.chulkova.restaurantvoting.repository.UserRepository;
import ru.chulkova.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.chulkova.restaurantvoting.web.UserTestUtil.*;
import static ru.chulkova.restaurantvoting.web.admin.AdminMealController.ADMIN_MEAL_URL;
import static ru.chulkova.restaurantvoting.web.user.AccountController.URL;

class AccountControllerTest extends AbstractControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isUnauthorized());
    }

    //TODO исправить нерабочий тест
    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL))
                .andExpect(status().isNoContent());
        Assertions.assertFalse(userRepository.findById(USER_ID).isPresent());
        Assertions.assertTrue(userRepository.findById(ADMIN_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(ADMIN_MEAL_URL))
                .andExpect(status().isForbidden());
    }

    //TODO test update
//    @Test
//    @WithUserDetails(value = USER_MAIL)
//    void update() {
//        User updatedTo = new User("user@yandex.ru", "newName", "newPassword",
//                Set.of(Role.USER));
//        perform(MockMvcRequestBuilders.put(URL).contentType(MediaType.APPLICATION_JSON))
//                .content(JsonUtil.writeValue(updatedTo))
//                .andDo(print())
//                .andExpect(status().isNoContent());
//    }
}