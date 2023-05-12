package ru.chulkova.restaurantvoting.service;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.chulkova.restaurantvoting.model.Meal;
import ru.chulkova.restaurantvoting.repository.MealRepository;

import static ru.chulkova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
@Slf4j
public class MealService {

    private final MealRepository repository;

    public void update(Meal meal, int id) throws IllegalArgumentException, NotFoundException {
        log.info("update restaurant {} with id {}", meal, id);
        Assert.notNull(meal, "meal must not be null");
        checkNotFoundWithId(repository.save(meal), id);
    }
}
