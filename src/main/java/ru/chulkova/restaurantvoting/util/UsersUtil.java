package ru.chulkova.restaurantvoting.util;

import ru.chulkova.restaurantvoting.model.User;
import ru.chulkova.restaurantvoting.to.UserTo;

import java.util.List;

public class UsersUtil {

    public static UserTo getTo(User user) {
        return new UserTo(user.getId(), user.getName(),
                user.getEmail(), user.getPassword());
    }

    public static List<UserTo> getTos(List<User> users) {
        return users.stream().map(UsersUtil::getTo).toList();
    }
}
