package ru.chulkova.restaurantvoting.util;

import ru.chulkova.restaurantvoting.model.Meal;
import ru.chulkova.restaurantvoting.to.MealTo;

import java.util.List;

public class MealsUtil {
    public static MealTo getTo(Meal meal) {
        return new MealTo(meal.getId(), meal.getPrice(), meal.getDishName());
    }

    public static List<MealTo> getTos(List<Meal> meals) {
        return meals.stream().map(MealsUtil::getTo).toList();
    }
}
