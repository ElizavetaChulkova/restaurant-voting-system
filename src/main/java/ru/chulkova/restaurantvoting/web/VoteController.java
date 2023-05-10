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
import ru.chulkova.restaurantvoting.service.VoteService;
import ru.chulkova.restaurantvoting.to.VoteTo;
import ru.chulkova.restaurantvoting.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/account/vote", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Vote Controller")
public class VoteController {

    private final VoteService service;

    @PostMapping("/{id}")
    public ResponseEntity<VoteTo> vote(@PathVariable("id") int restId,
                                       @AuthenticationPrincipal AuthUser authUser) {
        ValidationUtil.assureIdConsistent(authUser.getUser(), authUser.id());
        Vote newVote = new Vote(LocalDate.now(), LocalTime.now(), authUser.id(), restId);
        VoteTo voteTo = service.getTo(newVote);
        try {
            service.createOrUpdate(authUser.id(), restId);
            return new ResponseEntity<>(voteTo, HttpStatus.CREATED);
        } catch (UnsupportedOperationException e) {
            return new ResponseEntity<>(service.getUserVoteByDate(authUser.id(),
                    newVote.getVoteDate()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping(value = "/all-votes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<VoteTo> getAllUserVotes(@AuthenticationPrincipal AuthUser authUser) {
        log.info("getAllUserVotes userId = {}", authUser.id());
        return service.getAllUserVotes(authUser.id());
    }
}
