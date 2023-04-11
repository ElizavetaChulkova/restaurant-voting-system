package ru.chulkova.restaurantvoting.web.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.chulkova.restaurantvoting.model.Meal;
import ru.chulkova.restaurantvoting.repository.MealRepository;
import ru.chulkova.restaurantvoting.to.MealTo;
import ru.chulkova.restaurantvoting.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminMealController {

    private final MealRepository mealRepository;

    @GetMapping(value = "/meals", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        log.info("getAllMeals {}", mealRepository.findAll());
        return MealTo.getTos(mealRepository.findAll());
    }

    @DeleteMapping("/meals/{mealId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("mealId") int mealId) {
        log.info("delete meal {}", mealId);
        mealRepository.delete(mealId);
    }

    @PostMapping(value = "/restaurants/{id}/new-meal", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<MealTo> create(@Valid @RequestBody Meal meal, @PathVariable("id") int restId) {
        ValidationUtil.checkNew(meal);
        log.info("create new meal {} for restaurant {}", meal, restId);
        meal.setRestaurantId(restId);
        mealRepository.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/restaurants/{id}/meals")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(MealTo.getTo(meal));
    }

    @PutMapping(value = "/meals/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Meal meal, @PathVariable("id") int id) {
        ValidationUtil.assureIdConsistent(meal, id);
        log.info("update meal {} to {}", mealRepository.findById(id), meal);
        mealRepository.save(meal);
    }

    @GetMapping(value = "/restaurants/{id}/meals", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAllByRestaurantId(@PathVariable("id") int restId) {
        log.info("getAll meals for restaurant {}", restId);
        return MealTo.getTos(mealRepository.getAll(restId));
    }
}
