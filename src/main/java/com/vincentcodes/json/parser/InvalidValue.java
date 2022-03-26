package com.vincentcodes.json.parser;

public class InvalidValue extends RuntimeException{

    public InvalidValue() {
        super();
    }

    public InvalidValue(String message) {
        super(message);
    }

    public InvalidValue(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidValue(Throwable cause) {
        super(cause);
    }

}
