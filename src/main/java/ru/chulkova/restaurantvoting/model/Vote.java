package ru.chulkova.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {

    private LocalDateTime voteDateTime;

    private User user;

    private Restaurant restaurant;
}
