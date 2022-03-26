package com.vincentcodes.json.lexer;

public class InvalidToken extends RuntimeException{

    public InvalidToken() {
        super();
    }

    public InvalidToken(String message) {
        super(message);
    }

    public InvalidToken(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidToken(Throwable cause) {
        super(cause);
    }

}
