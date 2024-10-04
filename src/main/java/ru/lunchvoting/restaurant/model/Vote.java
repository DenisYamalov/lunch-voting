package ru.lunchvoting.restaurant.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.lunchvoting.common.model.BaseEntity;
import ru.lunchvoting.user.model.User;

import java.time.LocalDate;

@Entity
@Table(name = "vote")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"user", "restaurant"})
public class Vote extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @Column(name = "vote_date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate voteDate;

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate voteDate) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.voteDate = voteDate;
    }
}
