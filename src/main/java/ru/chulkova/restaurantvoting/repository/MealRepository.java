package ru.chulkova.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.restaurantvoting.model.Meal;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {

    @Override
    @Transactional
    Meal save(Meal meal);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.restaurant.id=:restId and m.id=:id")
    int delete(@Param("restId") int restId, @Param("id") int id);
}
