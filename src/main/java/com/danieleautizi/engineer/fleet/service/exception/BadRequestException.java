package com.danieleautizi.engineer.fleet.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 400 Bad Request exception.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends AbstractStatusCodeException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(final String msg) {
        super(msg);
    }

    public BadRequestException(final String msg, final Throwable trbl) {
        super(msg, trbl);
    }

}
