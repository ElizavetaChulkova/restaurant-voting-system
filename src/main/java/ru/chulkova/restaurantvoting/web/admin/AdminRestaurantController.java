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
import ru.chulkova.restaurantvoting.service.RestaurantService;
import ru.chulkova.restaurantvoting.to.RestaurantTo;
import ru.chulkova.restaurantvoting.util.RestaurantsUtil;
import ru.chulkova.restaurantvoting.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Admin Restaurant Controller")
public class AdminRestaurantController {

    public static final String ADMIN_REST_URL = "/api/admin/restaurants";
    private final RestaurantRepository repository;
    private final MealRepository mealRepository;
    private final RestaurantService service;

    @GetMapping(value = "/{restaurantId}")
    public Restaurant get(@PathVariable("restaurantId") int id) {
        log.info("get restaurant with id = {}", id);
        return repository.getWithMeals(id);
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTo>> getAll() {
        List<RestaurantTo> rests = RestaurantsUtil.getTos(repository.getAll());
        log.info("get all restaurants");
        return new ResponseEntity<>(rests, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("restaurantId") int id) {
        log.info("delete restaurant by id = {}", id);
        repository.deleteExisted(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        ValidationUtil.checkNew(restaurant);
        log.info("create new restaurant {}", restaurant);
        repository.save(restaurant);
        List<Meal> newMeals = restaurant.getMenu();
        mealRepository.saveAll(newMeals);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/restaurants/{restaurantId}")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurant);
    }

    @PutMapping(value = "/{restaurantId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant,
                       @PathVariable("restaurantId") int id) throws NotFoundException {
        ValidationUtil.assureIdConsistent(restaurant, id);
        log.info("update restaurant with id = {}", id);
        service.update(restaurant, id);
    }
}
