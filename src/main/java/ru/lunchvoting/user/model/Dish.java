package ru.lunchvoting.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import ru.lunchvoting.common.model.NamedEntity;

@Entity
@Table(name = "dish")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Dish extends NamedEntity {
    /**
     * price in cents
     */
    @Column(name = "price", nullable = false)
    @NotNull
    @Size(min = 1)
    private Long price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    @NotNull
    private Restaurant restaurant;

    public Dish(Integer id, String name, Restaurant restaurant, Long price) {
        super(id, name);
        this.restaurant = restaurant;
        this.price = price;
    }
}
