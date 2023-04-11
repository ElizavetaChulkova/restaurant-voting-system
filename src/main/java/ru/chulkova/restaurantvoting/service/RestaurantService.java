package ru.chulkova.restaurantvoting.service;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.chulkova.restaurantvoting.model.Restaurant;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;

import static ru.chulkova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
@Slf4j
public class RestaurantService {

    private final RestaurantRepository repository;

    public void update(Restaurant newRestaurant, int id) throws IllegalArgumentException, NotFoundException {
        log.info("update restaurant {} with id {}", newRestaurant, id);
        checkNotFoundWithId(repository.save(newRestaurant), id);
    }
}
