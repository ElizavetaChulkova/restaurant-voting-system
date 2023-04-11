package ru.chulkova.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
public class VoteTo {

    private String restaurantName;

    private LocalDateTime voteDateTime;
}
