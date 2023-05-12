package ru.chulkova.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
public class VoteTo {

    private Integer id;

    private String restaurantName;

    //https://stackoverflow.com/questions/58201556/how-to-change-timestamp-field-format-for-responseentity
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime voteDateTime;
}
