package ru.chulkova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "meal", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"dish_name", "date"}, name = "one_unique_meal_per_day_idx")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meal extends BaseEntity implements Serializable {

    @Column(name = "price")
    @NotNull
    private int price;

    @Column(name = "dish_name")
    @Size(max = 128)
    @NotNull
    private String dishName;

    @Column(name = "date")
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Restaurant restaurant;

    public Meal(Integer id, Integer price, String dishName,
                LocalDate date, Restaurant restaurant) {
        this(price, dishName, date, restaurant);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Dish{" + "name='" + dishName + '\'' + ", date=" +
                date + ", price=" + price + ", restaurant=" + restaurant + '}';
    }
}
