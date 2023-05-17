package ru.chulkova.restaurantvoting.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class VoteUpdatingException extends AppException {
    public VoteUpdatingException(String msg) {
        super(HttpStatus.FORBIDDEN, msg, ErrorAttributeOptions.of(MESSAGE));
    }
}
