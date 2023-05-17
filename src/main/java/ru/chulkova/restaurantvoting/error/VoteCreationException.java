package ru.chulkova.restaurantvoting.error;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class VoteCreationException extends AppException {
    public VoteCreationException(String msg) {
        super(HttpStatus.FORBIDDEN, msg, ErrorAttributeOptions.of(MESSAGE));
    }
}
