package ru.chulkova.restaurantvoting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class Vote extends BaseEntity {

    @Column(name = "vote_date", columnDefinition = "timestamp default CURRENT_DATE")
    @NotNull
    private LocalDate voteDate;

    @Column(name = "vote_time", columnDefinition = "timestamp default CURRENT_TIME")
    @NotNull
    private LocalTime voteTime;

    @Column(name = "user_id", nullable = false)
    @JoinColumn(name = "user_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer userId;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer restaurantId;

    public static final LocalTime NO_CHANGE_TIME = LocalTime.of(11, 0);
}
