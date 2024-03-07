package io.github.vcvitaly.mazebank.exception;

public class MalformedDataException extends RuntimeException {

    public MalformedDataException(String message) {
        super(message);
    }

    public MalformedDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public MalformedDataException(Throwable cause) {
        super(cause);
    }
}
