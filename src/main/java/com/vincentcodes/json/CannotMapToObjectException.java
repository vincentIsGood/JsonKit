package com.vincentcodes.json;

public class CannotMapToObjectException extends ReflectiveOperationException {
    public CannotMapToObjectException() {
    }

    public CannotMapToObjectException(String message) {
        super(message);
    }

    public CannotMapToObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotMapToObjectException(Throwable cause) {
        super(cause);
    }
}
