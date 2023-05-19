package ru.chulkova.restaurantvoting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

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
public class Vote extends BaseEntity implements Serializable {

    @Column(name = "vote_date", columnDefinition = "timestamp default CURRENT_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate voteDate;

    @Column(name = "vote_time", columnDefinition = "timestamp default CURRENT_TIME")
    @NotNull
    private LocalTime voteTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    public Vote(Integer id, LocalDate voteDate, LocalTime voteTime, User user, Restaurant restaurant) {
        this(voteDate, voteTime, user, restaurant);
        this.id = id;
    }

    @Override
    public String toString() {
        return "Vote: " + id + '[' + voteDate + ',' +
                voteTime + ',' + user + ',' + restaurant + ']';
    }
}
