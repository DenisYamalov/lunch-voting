package ru.lunchvoting.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import ru.lunchvoting.common.model.NamedEntity;

import java.time.LocalDate;

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
    @Range(min = 1)
    private Long price;

    //To save history
    @Column(name = "menu_date", nullable = false)
    @NotNull
    private LocalDate menuDate;

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

    public Dish(Integer id, String name, LocalDate menuDate, Long price, Restaurant restaurant) {
        super(id, name);
        this.menuDate = menuDate;
        this.price = price;
        this.restaurant = restaurant;
    }
}
