package io.github.vcvitaly.mazebank.exception;

public class MalformedClientDataException extends RuntimeException {

    public MalformedClientDataException(String message) {
        super(message);
    }

    public MalformedClientDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedClientDataException(Throwable cause) {
        super(cause);
    }
}
