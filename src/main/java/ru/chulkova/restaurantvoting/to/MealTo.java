package ru.chulkova.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@ToString
@EqualsAndHashCode(callSuper = true)
public class MealTo extends BaseTo {

    int price;

    @NotBlank
    @Size(min = 2, max = 100)
    String name;

    public MealTo(Integer id, int price, String name) {
        super(id);
        this.price = price;
        this.name = name;
    }
}
