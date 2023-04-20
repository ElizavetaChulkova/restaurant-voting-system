package ru.chulkova.restaurantvoting.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.chulkova.restaurantvoting.model.BaseEntity;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
public class VoteTo {

    Integer id;

    private String restaurantName;

    private LocalDateTime voteDateTime;
}
