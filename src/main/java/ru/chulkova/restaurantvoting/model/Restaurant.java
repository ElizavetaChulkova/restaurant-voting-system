package ru.chulkova.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Restaurant extends BaseEntity {

    @Column(name = "name")
    @Size(max = 128)
    @NotBlank
    private String name;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @JsonManagedReference
    private List<Meal> menu;

    public Restaurant(Integer id, String name, List<Meal> menu) {
        this(name, menu);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Restaurant [name=" + name + "]";
    }
}
