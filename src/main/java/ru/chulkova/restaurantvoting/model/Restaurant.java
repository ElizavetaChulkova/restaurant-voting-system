package ru.chulkova.restaurantvoting.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractPersistable;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends AbstractPersistable<Integer> {

    @Column(name = "name")
    @Size(max = 128)
    private String name;

    @Column(name = "menu")
    @CollectionTable(name = "meal",
            joinColumns = @JoinColumn(name = "restaurant_id"))
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "restaurant")
    private List<Meal> menu;

    @Column(name = "date")
    @NotNull
    private LocalDate date;
}
