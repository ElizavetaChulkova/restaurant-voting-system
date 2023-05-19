package ru.chulkova.restaurantvoting.web.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.restaurantvoting.model.User;
import ru.chulkova.restaurantvoting.repository.UserRepository;
import ru.chulkova.restaurantvoting.to.UserTo;
import ru.chulkova.restaurantvoting.web.AuthUser;

import javax.validation.Valid;

import static ru.chulkova.restaurantvoting.config.WebSecurityConfig.PASSWORD_ENCODER;
import static ru.chulkova.restaurantvoting.util.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = ProfileController.URL)
@Slf4j
@AllArgsConstructor
@Tag(name = "Profile Controller")
public class ProfileController {

    public static final String URL = "/api/account";
    private final UserRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get id = {}", authUser.id());
        return authUser.getUser();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete id = {}", authUser.id());
        repository.delete(authUser.getUser());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update user with id = {}", authUser.id());
        assureIdConsistent(userTo, authUser.id());
        User user = authUser.getUser();
        user.setName(userTo.getName());
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        repository.save(user);
    }
}
