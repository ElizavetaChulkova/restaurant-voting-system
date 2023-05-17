package ru.chulkova.restaurantvoting.web.user;

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
import ru.chulkova.restaurantvoting.util.ValidationUtil;

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

    @PostMapping("/{restaurantId}")
    public ResponseEntity<VoteTo> create(@PathVariable("restaurantId") int restId,
                                         @AuthenticationPrincipal AuthUser authUser) {
        Vote newVote = new Vote(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                authUser.id(), restId);
        ValidationUtil.checkNew(newVote);
        VoteTo voteTo = service.create(newVote);
        log.info("create vote for restId = {}, userId = {}", restId, authUser.id());
        return new ResponseEntity<>(voteTo, HttpStatus.CREATED);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<VoteTo> update(@PathVariable("restaurantId") int restId,
                                         @AuthenticationPrincipal AuthUser authUser) {
        Vote vote = repository.getVoteByDate(authUser.id(), LocalDate.now()).orElse(null);
        ValidationUtil.assureIdConsistent(vote, vote.id());
        if (vote != null) {
            vote.setVoteTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
            vote.setRestaurantId(restId);
            service.update(vote);
            log.info("update vote userId = {}, restId = {}", authUser.id(), restId);
            return new ResponseEntity<>(service.update(vote), HttpStatus.NO_CONTENT);
        } else {
            log.error("Not allowed to change vote");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/all-votes")
    public List<VoteTo> getAllUserVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAllUserVotes userId = {}", authUser.id());
        return service.getAllUserVotes(authUser.id());
    }
}