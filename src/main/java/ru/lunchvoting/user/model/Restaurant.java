package ru.lunchvoting.user.model;

import ru.lunchvoting.common.model.NamedEntity;

import java.util.List;

public class Restaurant extends NamedEntity {
    private List<Dish> menu;
}
