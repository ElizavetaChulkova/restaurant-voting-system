package ru.chulkova.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@ToString
@EqualsAndHashCode(callSuper = true)
public class RestaurantTo extends BaseTo {

    @NotBlank
    @Size(min = 2, max = 100)
    String name;

    public RestaurantTo(Integer id, String name) {
        super(id);
        this.name = name;
    }
}
