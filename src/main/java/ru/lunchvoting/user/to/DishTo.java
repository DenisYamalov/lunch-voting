package ru.lunchvoting.user.to;

import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.hibernate.validator.constraints.Range;
import ru.lunchvoting.common.HasId;
import ru.lunchvoting.common.to.NamedTo;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class DishTo extends NamedTo implements HasId {
    @NotNull
    @Range(min = 1)
    Long price;

    @NotNull
    LocalDate menuDate;

    public DishTo(Integer id, String name, LocalDate menuDate, Long price) {
        super(id, name);
        this.menuDate = menuDate;
        this.price = price;
    }

    @Override
    public String toString() {
        return "DishTo{" +
               "menuDate=" + menuDate +
               ", price=" + price +
               "}";
    }
}
