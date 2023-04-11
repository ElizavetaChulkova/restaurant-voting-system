package ru.chulkova.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.restaurantvoting.model.User;

import java.util.Optional;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    @Transactional
    User save(User user);

    @Transactional
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @RestResource(rel = "by-email", path = "by-email")
    @Query("SELECT u FROM User u WHERE u.email = LOWER(:email)")
    Optional<User> getByEmail(@Param("email") String email);
}
