package ru.chulkova.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.chulkova.restaurantvoting.model.Restaurant;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class RestaurantTo {

    private final String name;

    public static List<RestaurantTo> getTos(List<Restaurant> rests) {
        return rests.stream().map(restaurant -> new RestaurantTo(restaurant.getName())).toList();
    }

    public static RestaurantTo getTo(@NotNull Restaurant restaurant) {
        return new RestaurantTo(restaurant.getName());
    }

}
