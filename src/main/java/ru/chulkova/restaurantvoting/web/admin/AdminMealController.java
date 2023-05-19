package ru.chulkova.restaurantvoting.web.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.chulkova.restaurantvoting.model.Meal;
import ru.chulkova.restaurantvoting.model.Restaurant;
import ru.chulkova.restaurantvoting.repository.MealRepository;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;
import ru.chulkova.restaurantvoting.service.MealService;
import ru.chulkova.restaurantvoting.to.MealTo;
import ru.chulkova.restaurantvoting.util.MealsUtil;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api/admin/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Admin Meal Controller")
public class AdminMealController {

    public static final String ADMIN_MEAL_URL = "/api/admin/restaurants";
    private final MealRepository mealRepository;
    private final MealService service;
    private final RestaurantRepository restaurantRepository;

    @DeleteMapping("/{restaurantId}/meals/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int restId, @PathVariable("mealId") int mealId) {
        log.info("delete meal {}", mealId);
        mealRepository.delete(restId, mealId);
    }

    @PostMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<MealTo> create(@Valid @RequestBody Meal meal,
                                         @PathVariable("restaurantId") int restId) {
        log.info("create new meal {} for restaurant {}", meal, restId);
        Restaurant restaurant = restaurantRepository.getExisted(restId);
        restaurant.setMenuDate(LocalDate.now());
        meal.setRestaurant(restaurant);
        mealRepository.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/restaurants/{restaurantId}/meals")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(MealsUtil.getTo(meal));
    }

    @PutMapping(value = "/{restaurantId}/meals/{mealId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Meal meal, @PathVariable("restaurantId") int restId,
                       @PathVariable("mealId") int id) throws NotFoundException {
        Restaurant restaurant = restaurantRepository.getExisted(restId);
        restaurant.setMenuDate(LocalDate.now());
        meal.setRestaurant(restaurant);
        log.info("update meal with id {}", id);
        service.update(meal, id);
    }
}
