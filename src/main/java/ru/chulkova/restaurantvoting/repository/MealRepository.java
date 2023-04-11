package ru.chulkova.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.restaurantvoting.model.Meal;

import java.util.List;

@Transactional(readOnly = true)
public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Override
    @Transactional
    Meal save(Meal meal);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);

//    @Query("SELECT m FROM Meal m WHERE m.restaurantId=:restId and m.id=:id")
//    Meal getByRestaurant(@Param("id") int id, @Param("restId") int restId);

    @Query("SELECT m FROM Meal m WHERE m.restaurant.id=:restId")
    List<Meal> getAll(@Param("restId") int restId);
}
