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
import ru.chulkova.restaurantvoting.model.User;
import ru.chulkova.restaurantvoting.repository.UserRepository;
import ru.chulkova.restaurantvoting.service.UserService;
import ru.chulkova.restaurantvoting.to.UserTo;
import ru.chulkova.restaurantvoting.util.ValidationUtil;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/admin/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Admin Users Controller")
public class AdminUsersController {

    private UserRepository repository;
    private UserService service;

    @GetMapping
    public ResponseEntity<List<UserTo>> getAll() {
        List<UserTo> users = UserTo.getTos(repository.findAll());
        log.info("get all users {}", users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserTo get(@PathVariable("id") int id) {
        log.info("get user {} by id= {}", repository.findById(id), id);
        return UserTo.getTo(repository.findById(id).orElseThrow());
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int id) {
        log.info("delete user by id= {}", id);
        repository.deleteById(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserTo> create(@Valid @RequestBody User user) {
        ValidationUtil.checkNew(user);
        log.info("create new user {}", user);
        repository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/account/{id}")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(UserTo.getTo(user));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @PathVariable("id") int id) throws NotFoundException {
        ValidationUtil.assureIdConsistent(user, id);
        log.info("update restaurant from {} to {}", repository.findById(id), user);
        service.update(user, id);
    }
}
