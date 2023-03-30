package ru.chulkova.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "restaurant_name_idx")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends BaseEntity {

    @Column(name = "name")
    @Size(max = 128)
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantId")
    private List<Meal> menu;
}
