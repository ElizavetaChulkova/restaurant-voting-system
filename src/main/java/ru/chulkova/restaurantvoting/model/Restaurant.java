package ru.chulkova.restaurantvoting.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restaurant", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "restaurant_name_idx")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends BaseEntity implements Serializable {

    @Column(name = "name")
    @Size(max = 128)
    @NotBlank
    @NotNull
    private String name;

    @Column(name = "menu_date")
    @NotNull
    private LocalDate menuDate;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @ApiModelProperty(hidden = true)
    private List<Meal> menu;

    public Restaurant(Integer id, String name, LocalDate menuDate, List<Meal> menu) {
        this(name, menuDate, menu);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Restaurant [name=" + name + "]";
    }
}
