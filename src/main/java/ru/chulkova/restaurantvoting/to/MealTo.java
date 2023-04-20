package ru.chulkova.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.chulkova.restaurantvoting.model.BaseEntity;
import ru.chulkova.restaurantvoting.model.Meal;

import java.util.List;

@Data
@AllArgsConstructor
@ToString
public class MealTo {

    private Integer id;

    private int price;

    private String dishName;

    public static MealTo getTo(Meal meal) {
        return new MealTo(meal.getId(), meal.getPrice(), meal.getDishName());
    }

    public static List<MealTo> getTos(List<Meal> meals) {
        return meals.stream().map(MealTo::getTo).toList();
    }
}
