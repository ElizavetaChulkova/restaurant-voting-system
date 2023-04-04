package ru.chulkova.restaurantvoting.service;

import org.springframework.stereotype.Service;
import ru.chulkova.restaurantvoting.model.Restaurant;
import ru.chulkova.restaurantvoting.model.Vote;
import ru.chulkova.restaurantvoting.repository.UserRepository;
import ru.chulkova.restaurantvoting.repository.VoteRepository;

import java.time.LocalDateTime;

@Service
public class VoteService {

    private final VoteRepository voteRepo;
    private final UserRepository userRepo;

    public VoteService(VoteRepository voteRepo, UserRepository userRepo) {
        this.voteRepo = voteRepo;
        this.userRepo = userRepo;
    }

    public Vote voteForRestaurant(LocalDateTime voteDateTime, int userId, Restaurant restaurant) {
        Vote vote = voteRepo.getVoteByDate(userId, voteDateTime.toLocalDate()).orElse(null);
        if (vote != null) {
            if (isAbleToChangeVote(voteDateTime)) {
                vote.setRestaurantId(restaurant.getId());
                vote.setVoteDateTime(voteDateTime);
                voteRepo.save(vote);
            }
        }
        return vote;
    }

    public boolean isAbleToChangeVote(LocalDateTime time) {
        if (time.toLocalTime().isBefore(Vote.NO_CHANGE_TIME)) {
            return true;
        } else return false;
    }
}
