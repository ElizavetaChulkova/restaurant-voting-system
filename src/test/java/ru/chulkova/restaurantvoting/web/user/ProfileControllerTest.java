package ru.chulkova.restaurantvoting.web.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.chulkova.restaurantvoting.model.Role;
import ru.chulkova.restaurantvoting.model.User;
import ru.chulkova.restaurantvoting.repository.UserRepository;
import ru.chulkova.restaurantvoting.to.UserTo;
import ru.chulkova.restaurantvoting.web.AbstractControllerTest;
import ru.chulkova.restaurantvoting.web.TestUtil;

import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.chulkova.restaurantvoting.util.JsonUtil.writeValue;
import static ru.chulkova.restaurantvoting.web.TestUtil.USER_ID;
import static ru.chulkova.restaurantvoting.web.TestUtil.USER_MAIL;
import static ru.chulkova.restaurantvoting.web.user.ProfileController.URL;

class ProfileControllerTest extends AbstractControllerTest {

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

    @Test
    @WithUserDetails(value = USER_MAIL)
    void delete() throws Exception {
        Integer expected = userRepository.findAll().size();
        perform(MockMvcRequestBuilders.delete(URL))
                .andExpect(status().isNoContent());
        Assertions.assertNotEquals(expected, userRepository.findAll().size());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        UserTo updated = new UserTo(USER_ID, "new name", USER_MAIL,
                "newPassword");
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());
        User user = new User(USER_ID, updated.getEmail(),
                updated.getName(), updated.getPassword(), Set.of(Role.USER));
        TestUtil.assertEquals(user, userRepository.getExisted(USER_ID));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateHtmlUnsafe() throws Exception {
        User updated = new User(USER_ID, "newemail@gmailcom", "new name",
                "newpassword", Set.of(Role.USER));
        updated.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}