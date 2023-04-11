package ru.chulkova.restaurantvoting.web.admin;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.chulkova.restaurantvoting.model.Restaurant;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;
import ru.chulkova.restaurantvoting.to.RestaurantTo;
import ru.chulkova.restaurantvoting.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/admin/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminRestaurantController {

    private final RestaurantRepository repository;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RestaurantTo get(@PathVariable("id") int id) {
        log.info("getAll restaurants {}", repository.findById(id));
        return RestaurantTo.getTo(repository.findById(id).orElseThrow());
    }

    @GetMapping
    public ResponseEntity<List<RestaurantTo>> getAll(){
        log.info("get all restaurants {}");
        return new ResponseEntity<>(RestaurantTo.getTos(repository.getAll()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete restaurant {}", id);
        repository.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestaurantTo> create(@Valid @RequestBody RestaurantTo restaurantTo) {
        Restaurant rest = new Restaurant(restaurantTo.getName(), null);
        ValidationUtil.checkNew(rest);
        log.info("create new restaurant {}", rest);
        repository.save(rest);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/admin/restaurants/{id}")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(restaurantTo);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable("id") int id) {
        ValidationUtil.assureIdConsistent(restaurant, id);
        log.info("update restaurant from {} to {}", repository.findById(id), restaurant);
        repository.save(restaurant);
    }

}
