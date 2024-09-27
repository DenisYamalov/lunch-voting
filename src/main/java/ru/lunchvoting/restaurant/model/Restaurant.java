package ru.lunchvoting.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.lunchvoting.common.model.NamedEntity;

import java.util.List;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity {

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy("name DESC")
    @JsonIgnore
    private List<Dish> menu;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
