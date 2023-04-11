package ru.chulkova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "restaurant_name_idx")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Restaurant extends BaseEntity {

    @Column(name = "name")
    @Size(max = 128)
    @NotBlank
    private String name;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurantId")
    @JsonManagedReference
    private List<Meal> menu;
}
