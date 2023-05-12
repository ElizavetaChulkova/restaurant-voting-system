package ru.chulkova.restaurantvoting.web;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.restaurantvoting.model.Vote;
import ru.chulkova.restaurantvoting.repository.VoteRepository;
import ru.chulkova.restaurantvoting.service.VoteService;
import ru.chulkova.restaurantvoting.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping(value = "/api/account/vote", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Vote Controller")
public class VoteController {

    private final VoteService service;

    private final VoteRepository repository;

    @PostMapping("/{id}")
    public ResponseEntity<VoteTo> create(@PathVariable("id") int restId,
                                         @AuthenticationPrincipal AuthUser authUser) {
        Vote newVote = new Vote(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                authUser.id(), restId);
        VoteTo voteTo = service.create(newVote);
        return new ResponseEntity<>(voteTo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoteTo> update(@PathVariable("id") int restId,
                                         @AuthenticationPrincipal AuthUser authUser) {
        Vote vote = repository.getVoteByDate(authUser.id(), LocalDate.now()).orElse(null);
        if (vote != null) {
            vote.setVoteTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
            vote.setRestaurantId(restId);
            service.update(vote);
            return new ResponseEntity<>(service.update(vote), HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping(value = "/all-votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteTo> getAllUserVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAllUserVotes userId = {}", authUser.id());
        return service.getAllUserVotes(authUser.id());
    }
}
