package ru.chulkova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "meal")
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

    @Column(name = "menu_date")
    @NotNull
    private LocalDate menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;

    public Meal(Integer id, Integer price, String dishName, LocalDate menuDate, Restaurant restaurant) {
        this(price, dishName, menuDate, restaurant);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Dish{" + "name='" + dishName + '\'' + ", date=" + menuDate + ", price=" + price + ", restaurant="
                + restaurant + '}';
    }
}
