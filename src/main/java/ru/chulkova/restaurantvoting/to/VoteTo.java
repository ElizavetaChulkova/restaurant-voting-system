package ru.chulkova.restaurantvoting.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Value
@ToString
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    String restaurantName;

    //https://stackoverflow.com/questions/58201556/how-to-change-timestamp-field-format-for-responseentity
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm")
    LocalDateTime voteDateTime;

    public VoteTo(Integer id, String name, LocalDateTime voteDateTime) {
        super(id);
        this.restaurantName = name;
        this.voteDateTime = voteDateTime;
    }
}
