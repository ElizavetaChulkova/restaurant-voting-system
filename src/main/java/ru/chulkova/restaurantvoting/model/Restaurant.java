package ru.chulkova.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    private List<Meal> menu;

    private LocalDate date;
}
