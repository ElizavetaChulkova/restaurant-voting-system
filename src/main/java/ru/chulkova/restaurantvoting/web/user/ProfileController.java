package ru.chulkova.restaurantvoting.web.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.restaurantvoting.model.User;
import ru.chulkova.restaurantvoting.repository.UserRepository;
import ru.chulkova.restaurantvoting.util.ValidationUtil;

import javax.validation.Valid;

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
    @CacheEvict(value = "users")
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete id = {}", authUser.id());
        repository.deleteById(authUser.id());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CachePut(value = "users")
    public User update(@Valid @RequestBody User user, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update user with id = {}", authUser.id());
        User oldUser = authUser.getUser();
        ValidationUtil.assureIdConsistent(user, oldUser.id());
        user.setRoles(oldUser.getRoles());
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        return repository.save(user);
    }
}
