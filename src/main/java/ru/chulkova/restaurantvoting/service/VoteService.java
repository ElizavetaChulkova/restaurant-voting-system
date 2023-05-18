package ru.chulkova.restaurantvoting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chulkova.restaurantvoting.error.VoteCreationException;
import ru.chulkova.restaurantvoting.error.VoteUpdatingException;
import ru.chulkova.restaurantvoting.model.Vote;
import ru.chulkova.restaurantvoting.repository.VoteRepository;
import ru.chulkova.restaurantvoting.to.VoteTo;

import java.time.LocalDate;
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

    @Transactional
    public VoteTo create(Vote vote) {
        if (isVoteToday(vote.getUser().id())) {
            throw new VoteCreationException("You have already voted today. Only one vote per day is allowed.");
        }
        Vote newVote = voteRepo.save(vote);
        return getTo(newVote);
    }

    @Transactional
    public VoteTo update(Vote vote) {
        vote.setVoteTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        if (isAbleToChange(vote.getVoteTime().truncatedTo(ChronoUnit.MINUTES))) {
            voteRepo.save(vote);
        } else throw new VoteUpdatingException("You are not allowed to change your vote after 11 am.");
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

    public boolean isVoteToday(int userId) {
        return voteRepo.getByDate(userId, LocalDate.now()).isPresent();
    }
}
