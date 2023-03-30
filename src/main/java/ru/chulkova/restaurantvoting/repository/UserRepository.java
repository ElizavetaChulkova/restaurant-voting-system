package ru.chulkova.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.chulkova.restaurantvoting.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
