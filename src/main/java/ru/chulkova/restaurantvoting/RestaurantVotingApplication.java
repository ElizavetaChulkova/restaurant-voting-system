package ru.chulkova.restaurantvoting;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.chulkova.restaurantvoting.repository.RestaurantRepository;
import ru.chulkova.restaurantvoting.repository.UserRepository;
import ru.chulkova.restaurantvoting.repository.VoteRepository;
import ru.chulkova.restaurantvoting.service.VoteService;

import java.time.LocalDate;

@SpringBootApplication
@AllArgsConstructor
public class RestaurantVotingApplication implements ApplicationRunner {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteService voteService;
    private final VoteRepository voteRepo;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        System.out.println(userRepository.findAll());
        System.out.println(restaurantRepository.getAllWithMeals());
//        System.out.println(voteService.createOrUpdate(10, 2));
//        System.out.println(voteService.getAllUserVotes(1));
//        System.out.println(voteRepo.getVoteByDate(1, LocalDate.now()));
//        System.out.println(voteService.getUserVoteByDate(1 , LocalDate.now()));
//        System.out.println(restaurantRepository.findById(1));
    }
}
