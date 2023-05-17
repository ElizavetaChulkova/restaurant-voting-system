package ru.chulkova.restaurantvoting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Value
@ToString
@EqualsAndHashCode(callSuper = true)
public class MealTo extends BaseTo {
    
    int price;

    @NotBlank
    @Size(min = 2, max = 100)
    String dishName;

    public MealTo(Integer id, int price, String dishName) {
        super(id);
        this.price = price;
        this.dishName = dishName;
    }
}
