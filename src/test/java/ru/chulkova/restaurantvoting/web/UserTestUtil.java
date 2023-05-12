package ru.chulkova.restaurantvoting.web;

import ru.chulkova.restaurantvoting.model.BaseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTestUtil {

    public static final Integer USER_ID = 1;
    public static final String USER_MAIL = "user@gmail.com";
    public static final Integer ADMIN_ID = 2;
    public static final String ADMIN_MAIL = "admin@javaops.ru";
    public static final Integer MEAL_ID = 1;
    public static final Integer REST_ID = 1;
    public static final Integer VOTE_ID = 3;

    public static void assertEquals(BaseEntity actual, BaseEntity expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("password").isEqualTo(expected);
    }

    public static void assertNoIdEquals(BaseEntity actual, BaseEntity expected) {
        assertThat(actual).usingRecursiveComparison().ignoringFields("id", "password").isEqualTo(expected);
    }
}
