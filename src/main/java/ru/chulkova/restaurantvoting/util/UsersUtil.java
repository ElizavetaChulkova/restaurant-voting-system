package ru.chulkova.restaurantvoting.util;

import ru.chulkova.restaurantvoting.model.User;
import ru.chulkova.restaurantvoting.to.UserTo;

import java.util.List;

import static ru.chulkova.restaurantvoting.config.WebSecurityConfig.PASSWORD_ENCODER;

public class UsersUtil {

    public static UserTo getTo(User user) {
        return new UserTo(user.getId(), user.getName(),
                user.getEmail(), user.getPassword());
    }

    public static List<UserTo> getTos(List<User> users) {
        return users.stream().map(UsersUtil::getTo).toList();
    }

    public static User updateFromTo(User user, UserTo userTo) {
        user.setName(userTo.getName());
        user.setEmail(userTo.getEmail().toLowerCase());
        user.setPassword(userTo.getPassword());
        return user;
    }

    public static User prepareToSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return user;
    }
}
