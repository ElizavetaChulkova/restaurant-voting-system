package ru.chulkova.restaurantvoting.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.chulkova.restaurantvoting.model.Restaurant;
import ru.chulkova.restaurantvoting.model.Role;
import ru.chulkova.restaurantvoting.model.User;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;
import ru.chulkova.restaurantvoting.repository.UserRepository;
import ru.chulkova.restaurantvoting.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/root", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Root Controller")
public class RootController {

    private final UserRepository repository;
    private final RestaurantRepository restaurantRepository;

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        log.info("register {}", user);
        ValidationUtil.checkNew(user);
        user.setRoles(Set.of(Role.USER));
        user = repository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/account")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(user);
    }

    @GetMapping(value = "/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurantsWithMeals() {
        log.info("getRestaurantsWithMeals {}", restaurantRepository.getAllWithMeals());
        List<Restaurant> restaurants = restaurantRepository.getAllWithMeals();
        return new ResponseEntity<>(restaurants, HttpStatus.OK);
    }
}

