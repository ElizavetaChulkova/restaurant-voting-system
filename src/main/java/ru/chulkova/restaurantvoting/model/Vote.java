package ru.chulkova.restaurantvoting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "vote", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "date_time"},
        name = "one_user_vote_per_day_idx")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Vote extends BaseEntity {

    @Column(name = "date_time")
    @NotNull
    private LocalDateTime voteDateTime;

    @JoinColumn(name = "user_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer userId;

    @JoinColumn(name = "restaurant_id", nullable = false)
//    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Integer restaurantId;

    public static final LocalTime NO_CHANGE_TIME = LocalTime.of(11, 0);
}
