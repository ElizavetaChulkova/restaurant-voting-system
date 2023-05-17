package ru.chulkova.restaurantvoting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId and v.voteDate=:date")
    Optional<Vote> getByDate(@Param("userId") int userId, @Param("date") LocalDate date);

    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId")
    List<Vote> getAllByUserId(@Param("userId") int userId);

    @EntityGraph(attributePaths = {"restaurant"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("SELECT v from Vote v WHERE v.user.id=:userId")
    List<Vote> getAllUserVotes(@Param("userId") int userId);
}
