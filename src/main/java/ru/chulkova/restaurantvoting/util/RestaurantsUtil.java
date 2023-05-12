package ru.chulkova.restaurantvoting.util;

import ru.chulkova.restaurantvoting.model.Restaurant;
import ru.chulkova.restaurantvoting.to.RestaurantTo;

import javax.validation.constraints.NotNull;
import java.util.List;

public class RestaurantsUtil {
    public static List<RestaurantTo> getTos(List<Restaurant> rests) {
        return rests.stream().map(restaurant -> new RestaurantTo(restaurant.getId(), restaurant.getName())).toList();
    }

    public static RestaurantTo getTo(@NotNull Restaurant restaurant) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName());
    }
}
