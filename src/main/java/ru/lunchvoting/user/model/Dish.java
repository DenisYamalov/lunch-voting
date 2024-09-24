package ru.lunchvoting.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
@AllArgsConstructor
public class Dish extends NamedEntity {
    /**
     * price in cents
     */
    @Column(name = "price", nullable = false)
    @NotNull
    @Range(min = 1)
    private Long price;

    //To save history
    @Schema(hidden = true)
    @Column(name = "menu_date", nullable = false, columnDefinition = "date default now()")
    @NotNull
    private LocalDate menuDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Dish(Integer id, String name, Restaurant restaurant, Long price) {
        super(id, name);
        this.restaurant = restaurant;
        this.price = price;
        this.menuDate = LocalDate.now();
    }
}
