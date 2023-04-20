package ru.chulkova.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.chulkova.restaurantvoting.model.Meal;
import ru.chulkova.restaurantvoting.model.User;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class UserTo {

    private Integer id;

    private String name;

    private String email;

    public static UserTo getTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail());
    }

    public static List<UserTo> getTos(List<User> users) {
        return users.stream().map(UserTo::getTo).toList();
    }
}
