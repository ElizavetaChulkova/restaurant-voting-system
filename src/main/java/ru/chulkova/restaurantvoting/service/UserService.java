package ru.chulkova.restaurantvoting.service;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.chulkova.restaurantvoting.model.User;
import ru.chulkova.restaurantvoting.repository.UserRepository;

import static ru.chulkova.restaurantvoting.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private UserRepository repository;

    public void update(User user, int id) throws NotFoundException {
        log.info("update user {} with id {}", user, id);
        checkNotFoundWithId(repository.save(user), id);
    }
}
