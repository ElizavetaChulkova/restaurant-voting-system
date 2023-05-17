package ru.chulkova.restaurantvoting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.restaurantvoting.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Cacheable(value = "restaurants", key = "#restaurant.name")
    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    List<Restaurant> getAllWithMeals();

    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r WHERE r.id=:restId")
    Restaurant getWithMeals(@Param("restId") int restId);

    @Override
    @Transactional
//    @CachePut(value = "restaurants", key = "#restaurant.name")
    Restaurant save(Restaurant restaurant);

    @Override
    @Transactional
//    @CacheEvict(value = "restaurants", key = "#restaurant.name")
    void delete(Restaurant restaurant);

    @Query("SELECT r FROM Restaurant r ORDER BY r.name")
    List<Restaurant> getAll();

    @Query("SELECT r.name FROM Restaurant r WHERE r.id=:id")
    String getNameById(@Param("id") int restId);

    @Query("SELECT r FROM Restaurant r WHERE r.id=:id")
    Restaurant getById(@Param("id") int id);
}
