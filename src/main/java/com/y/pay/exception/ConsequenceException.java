package com.y.pay.exception;

/**
 * @author XiFYuW
 * @since  2020/10/20 14:17
 */
public class ConsequenceException extends RuntimeException {

    public ConsequenceException(String message) {
        super(message);
    }

    public ConsequenceException(String message, Throwable cause) {
        super(message, cause);
    }
}
