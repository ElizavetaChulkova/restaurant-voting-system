package ru.chulkova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Table(name = "meal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Meal extends BaseEntity {

    @Column(name = "price")
    @NotNull
    @Range(min = 10, max = 5000)
    private int price;

    @Column(name = "dish_name")
    @Size(max = 128)
    private String dishName;

    @Column(name = "menu_date")
    @NotNull
    private LocalDate menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    @Override
    public String toString() {
        return "Dish{" + "name='" + dishName + '\'' + ", date=" + menuDate + ", price=" + price + ", restaurant="
                + restaurant + '}';
    }
}
