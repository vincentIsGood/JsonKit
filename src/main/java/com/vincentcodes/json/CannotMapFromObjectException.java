package com.vincentcodes.json;

public class CannotMapFromObjectException extends ReflectiveOperationException {
    public CannotMapFromObjectException() {
    }

    public CannotMapFromObjectException(String message) {
        super(message);
    }

    public CannotMapFromObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public CannotMapFromObjectException(Throwable cause) {
        super(cause);
    }
}
