package com.vincentcodes.json.parser;

public class UnexpectedToken extends Exception{

    public UnexpectedToken() {
        super();
    }

    public UnexpectedToken(String message) {
        super(message);
    }

    public UnexpectedToken(String message, Throwable cause) {
        super(message, cause);
    }

    public UnexpectedToken(Throwable cause) {
        super(cause);
    }

}
