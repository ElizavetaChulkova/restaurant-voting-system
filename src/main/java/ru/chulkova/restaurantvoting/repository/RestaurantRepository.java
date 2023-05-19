package ru.chulkova.restaurantvoting.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    @CacheEvict(value = "restaurants")
    int delete(@Param("id") int id);

    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Cacheable(value = "restaurants")
    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    List<Restaurant> getAllWithMeals();

    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.menuDate=:date")
    @Cacheable(value = "restaurants")
    List<Restaurant> getWithMealsByDate(@Param("date") LocalDate date);

    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id=:restId")
    Restaurant getWithMeals(@Param("restId") int restId);

    @Override
    @Transactional
    @CachePut(value = "restaurants")
    Restaurant save(Restaurant restaurant);

    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    List<Restaurant> getAll();
}
