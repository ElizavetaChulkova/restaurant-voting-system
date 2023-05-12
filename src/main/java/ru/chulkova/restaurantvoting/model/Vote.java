package ru.chulkova.restaurantvoting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vote", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "user_vote_unique_idx")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Vote extends BaseEntity implements Serializable {

    public static final LocalTime NO_CHANGE_TIME = LocalTime.of(11, 0);

    @Column(name = "vote_date", columnDefinition = "timestamp default CURRENT_DATE")
    @NotNull
    private LocalDate voteDate;

    @Column(name = "vote_time", columnDefinition = "timestamp default CURRENT_TIME")
    @NotNull
    private LocalTime voteTime;

    @Column(name = "user_id", nullable = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Integer userId;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Integer restaurantId;

    public Vote(Integer id, LocalDate voteDate, LocalTime voteTime, Integer userId, Integer restaurantId) {
        this(voteDate, voteTime, userId, restaurantId);
        this.id = id;
    }
}
