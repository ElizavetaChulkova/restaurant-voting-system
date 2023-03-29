package ru.chulkova.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Table(name = "meal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Meal extends AbstractPersistable<Integer> {

    @Column(name = "price")
    @NotNull
    @Range(min = 10, max = 5000)
    private int price;

    @Column(name = "dish_name")
    @Size(max = 128)
    private int dishName;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;
}
