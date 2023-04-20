package ru.chulkova.restaurantvoting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.chulkova.restaurantvoting.model.Vote;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;
import ru.chulkova.restaurantvoting.repository.VoteRepository;
import ru.chulkova.restaurantvoting.to.VoteTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepo;

    private final RestaurantRepository restRepo;

    public VoteTo getUserVoteByDate(int userId, LocalDate date) {
        log.info("getUserVotesByDate: userId = {}, date = {}", userId, date);
        return getTo(Objects.requireNonNull(voteRepo.getVoteByDate(userId, date)
                .orElse(null)));
    }

    public List<VoteTo> getAllUserVotes(int userId) {
        List<Vote> userVotes = voteRepo.getAllUserVotes(userId);
        log.info("getAllUserVotes: userId = {}", userId);
        return userVotes.stream()
                .map(vote -> new VoteTo( vote.getId(), restRepo.getRestaurantNameById(vote.getRestaurantId()),
                        LocalDateTime.of(vote.getVoteDate(), vote.getVoteTime())))
                .toList();
    }

    public VoteTo createOrUpdate(int userId, int restId) {
//        ValidationUtil.assureIdConsistent(userRepository.getById(userId), userId);
        Vote vote = voteRepo.getVoteByDate(userId, LocalDate.now()).orElse(null);
        if (vote == null) {
            log.info("create vote: userId = {}, restaurantId = {}", userId, restId);
            vote = new Vote(LocalDate.now(), LocalTime.now(), userId, restId);
        } else {
            if (isAbleToChangeVote(LocalTime.now())) {
                log.info("update vote: userId = {}, restaurantId = {}", userId, restId);
                vote.setRestaurantId(restId);
                vote.setVoteDate(LocalDate.now());
                vote.setVoteTime(LocalTime.now());
            } else {
                log.info("not allowed to change vote");
                throw new UnsupportedOperationException("You are not allowed to change your vote");
            }
        }
        voteRepo.save(vote);
        return getTo(vote);
    }

    public VoteTo getTo(Vote vote) {
        return new VoteTo(vote.getId(), restRepo.getRestaurantNameById(vote.getRestaurantId()),
                LocalDateTime.of(vote.getVoteDate(), vote.getVoteTime()));
    }

    public boolean isAbleToChangeVote(LocalTime time) {
        if (time.isBefore(Vote.NO_CHANGE_TIME)) {
            log.info("allowed to change vote: time = {}", time);
            return true;
        } else return false;
    }
}
