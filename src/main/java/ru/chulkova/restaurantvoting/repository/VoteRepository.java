package ru.chulkova.restaurantvoting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.Optional;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Transactional
    Vote save(Vote vote);

    @Query("SELECT v FROM Vote v WHERE v.userId=:userId and v.voteDateTime=:date")
    Optional<Vote> getVoteByDate(@Param("userId") int userId, @Param("date") LocalDate date);
}
