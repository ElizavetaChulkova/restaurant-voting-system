package ru.chulkova.restaurantvoting.web.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.chulkova.restaurantvoting.error.NoVoteAtDateException;
import ru.chulkova.restaurantvoting.model.Vote;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;
import ru.chulkova.restaurantvoting.repository.VoteRepository;
import ru.chulkova.restaurantvoting.service.VoteService;
import ru.chulkova.restaurantvoting.to.VoteTo;
import ru.chulkova.restaurantvoting.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/account/vote", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Vote Controller")
public class VoteController {

    private final VoteService service;
    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;

    @PostMapping("/{restaurantId}")
    public ResponseEntity<VoteTo> create(@PathVariable("restaurantId") int restId,
                                         @AuthenticationPrincipal AuthUser authUser) {
        Vote newVote = new Vote(LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
                authUser.getUser(), restaurantRepository.getById(restId));
        ValidationUtil.checkNew(newVote);
        VoteTo voteTo = service.create(newVote);
        log.info("create vote for restId = {}, userId = {}", restId, authUser.id());
        return new ResponseEntity<>(voteTo, HttpStatus.CREATED);
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<VoteTo> update(@PathVariable("restaurantId") int restId,
                                         @AuthenticationPrincipal AuthUser authUser) {
        Vote vote = repository.getByDate(authUser.id(), LocalDate.now()).orElse(null);
        if (vote != null) {
            vote.setRestaurant(restaurantRepository.getById(restId));
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
        return service.getTos(repository.getAllUserVotes(authUser.id()));
    }

    @GetMapping(value = "/by-date")
    public VoteTo getByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                            @AuthenticationPrincipal AuthUser authUser) {
        log.info("getVoteByDate date = {}", date);
        Optional<Vote> vote = repository.getByDate(authUser.id(), date);
        if (vote.isEmpty()) {
            throw new NoVoteAtDateException("You didn't vote at this date");
        } else return service.getTo(vote.get());
    }
}
