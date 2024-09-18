package ru.lunchvoting.common.error;

import static ru.lunchvoting.common.error.ErrorType.NOT_FOUND;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg, NOT_FOUND);
    }
}