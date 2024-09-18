package ru.lunchvoting.user.model;


import ru.lunchvoting.common.model.BaseEntity;

import java.time.LocalDateTime;

public class Vote extends BaseEntity {
    private User user;
    private Restaurant restaurant;
    private LocalDateTime dateTime;
}
