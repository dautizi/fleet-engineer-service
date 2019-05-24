package com.danieleautizi.engineer.fleet.service.exception;

/**
 * Abstract Exception with support for status codes.
 */

public abstract class AbstractStatusCodeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Abstract constructor with message.
     * @param msg User-friendly message
     */
    public AbstractStatusCodeException(final String msg) {
        super(msg);
    }

    /**
     * Abstract constructor with message and throwable.
     * @param msg User-friendly message
     * @param trbl Geek-friendly throwable
     */
    public AbstractStatusCodeException(final String msg, final Throwable trbl) {
        super(msg, trbl);
    }

}
