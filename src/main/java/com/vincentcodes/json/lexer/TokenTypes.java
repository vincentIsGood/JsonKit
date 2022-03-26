package com.vincentcodes.json.lexer;

public enum TokenTypes {
    STRING,
    NUMBER,
    WHITESPACE,
    LETTER, // for true, false, null, e, E

    OPEN_CURLY_BRACKET,
    CLOSE_CURLY_BRACKET,

    OPEN_SQUARE_BRACKET,
    CLOSE_SQUARE_BRACKET,

    COLON,
    COMMA,

    PLUS,
    MINUS,

    EOF
}
