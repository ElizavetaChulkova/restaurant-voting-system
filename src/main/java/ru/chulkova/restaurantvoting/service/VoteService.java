package ru.chulkova.restaurantvoting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.restaurantvoting.model.Vote;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;
import ru.chulkova.restaurantvoting.repository.VoteRepository;
import ru.chulkova.restaurantvoting.to.VoteTo;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class VoteService {

    public static final LocalTime NO_CHANGE_TIME = LocalTime.of(11, 0);

    private final VoteRepository voteRepo;

    private final RestaurantRepository restRepo;

    @Transactional
    public VoteTo create(Vote vote) {
        Vote newVote = voteRepo.save(vote);
        return getTo(newVote);
    }

    @Transactional
    public VoteTo update(Vote vote) {
        if (isAbleToChange(LocalTime.now().truncatedTo(ChronoUnit.MINUTES))) {
            voteRepo.save(vote);
        } else throw new UnsupportedOperationException("You are not allowed to change your vote");
        return getTo(voteRepo.save(vote));
    }

    public VoteTo getTo(Vote vote) {
        return new VoteTo(vote.id(), vote.getRestaurant().getName(),
                LocalDateTime.of(vote.getVoteDate(), vote.getVoteTime()));
    }

    public List<VoteTo> getTos(List<Vote> votes) {
        return votes.stream().map(this::getTo).toList();
    }

    public static boolean isAbleToChange(LocalTime time) {
        if (time.isBefore(NO_CHANGE_TIME)) {
            log.info("allowed to change vote: time = {}", time);
            return true;
        } else return false;
    }
}
