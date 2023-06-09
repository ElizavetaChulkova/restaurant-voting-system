package ru.chulkova.restaurantvoting.web;

import ru.chulkova.restaurantvoting.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtil {

    public static final Integer USER_ID = 1;
    public static final String USER_MAIL = "user@gmail.com";
    public static final User user = new User(USER_ID, USER_MAIL, "User_First",
            "{noop}password", Set.of(Role.USER));

    public static final Integer ADMIN_ID = 2;
    public static final String ADMIN_MAIL = "admin@javaops.ru";
    public static final User admin = new User(ADMIN_ID, ADMIN_MAIL, "Admin_First",
            "{noop}admin", Set.of(Role.USER, Role.ADMIN));

    public static final Integer MEAL_ID = 1;
    public static final Integer VOTE_ID = 3;
    public static final Integer REST_ID = 1;
    public static final Restaurant restaurant = new Restaurant(2, "Lets carbonara",
            LocalDate.now(), null);
    {
        restaurant.setMenu(List.of(new Meal(111, "dish name", LocalDate.now(), restaurant)));
    }

    public static void assertEquals(BaseEntity actual, BaseEntity expected) {
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("password", "restaurant.menu", "menu").isEqualTo(expected);
    }

    public static void assertNoIdEquals(BaseEntity actual, BaseEntity expected) {
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id", "password", "restaurant.menu", "menu").isEqualTo(expected);
    }
}
