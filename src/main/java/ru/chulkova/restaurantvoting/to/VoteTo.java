package ru.chulkova.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@ToString
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    @NotNull
    Integer restaurantId;

    //https://stackoverflow.com/questions/58201556/how-to-change-timestamp-field-format-for-responseentity
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    LocalDateTime voteDateTime;

    public VoteTo(Integer id, Integer restaurantId, LocalDateTime voteDateTime) {
        super(id);
        this.restaurantId = restaurantId;
        this.voteDateTime = voteDateTime;
    }
}
