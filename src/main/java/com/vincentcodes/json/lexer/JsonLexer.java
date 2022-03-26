package com.vincentcodes.json.lexer;

/**
 * JsonLexer is used to tokenize some of the most
 * basic elements such as number, string, and others.
 *
 * See TokenTypes.java for more.
 *
 * @author vincent ko
 */
public class JsonLexer {
    private final String rawJson;
    private int currentTokenIndex;
    private int line;
    private Token currentToken;

    public JsonLexer(String rawJson){
        this.rawJson = rawJson;
        this.currentTokenIndex = 0;
        this.line = 1;
        this.currentToken = null;
    }

    public Token nextToken(){
        if(getCurrentChar() == '\0') {
            currentToken = new Token(TokenTypes.EOF, null);
            return new Token(TokenTypes.EOF, null);
        }
        StringBuilder value = new StringBuilder();
        char c = getCurrentChar();
        if(c == '"'){
            next();
            c = getCurrentChar();
            while(c != '"'){
                if(c == '\\'){
                    value.append(c);
                    next();
                    c = getCurrentChar();
                    if(!canCombineWithEscapeChar(c))
                        throw new InvalidToken("Cannot escape character '" + c + "' at position " + currentTokenIndex + ", line " + line);
                    else if(c == 'u'){
                        value.append(c);
                        next();
                        c = getCurrentChar();
                        int count = 3;
                        while(count >= 0){
                            if(isNumber(c) || isHexNum(c))
                                value.append(c);
                            else
                                throw new InvalidToken("Invalid unicode sequence '" + c + "' at position " + currentTokenIndex + ", line " + line);
                            count--;
                            next();
                            c = getCurrentChar();
                        }
                        continue;
                    }else{
                        value.append(c);
                    }
                }else
                    value.append(c);
                next();
                c = getCurrentChar();
            }
            next(); // skip "
            currentToken = new Token(TokenTypes.STRING, value.toString());
            return currentToken;
        }else if(isNumber(c)) {
            while(isNumber(c = getCurrentChar()) || allowedInfixInNumber(c)){
                value.append(c);
                next();
            }
            currentToken = new Token(TokenTypes.NUMBER, value.toString());
            return currentToken;
        }else if(isWhitespace(c)) {
            while (isWhitespace(c = getCurrentChar())) {
                if(c == '\n')
                    line++;
                next();
            }
            //currentToken = new Token(TokenTypes.WHITESPACE, "");
            return nextToken(); // skip whitespace
        }else if(isAllowedEnglishLetter(c)) {
            while (isAllowedEnglishLetter(c = getCurrentChar())) {
                value.append(c);
                next();
            }
            currentToken = new Token(TokenTypes.LETTER, value.toString());
            return currentToken;
        }else{
            Token result = null;
            switch (c){
                case '{': result = new Token(TokenTypes.OPEN_CURLY_BRACKET, Character.toString(c)); break;
                case '}': result = new Token(TokenTypes.CLOSE_CURLY_BRACKET, Character.toString(c)); break;
                case '[': result = new Token(TokenTypes.OPEN_SQUARE_BRACKET, Character.toString(c)); break;
                case ']': result = new Token(TokenTypes.CLOSE_SQUARE_BRACKET, Character.toString(c)); break;
                case ':': result = new Token(TokenTypes.COLON, Character.toString(c)); break;
                case ',': result = new Token(TokenTypes.COMMA, Character.toString(c)); break;
                case '+': result = new Token(TokenTypes.PLUS, Character.toString(c)); break;
                case '-': result = new Token(TokenTypes.MINUS, Character.toString(c)); break;
            }
            currentToken = result;
            next();
            if(result != null)
                return result;
        }
        throw new InvalidToken("Unrecognized token '" + c + "' at " + getLocation());
    }

    public char getCurrentChar(){
        if(currentTokenIndex == rawJson.length())
            return '\0';
        return rawJson.charAt(currentTokenIndex);
    }

    /**
     * @return TokenType.EOF if nextToken() is never called
     */
    public Token getCurrentToken(){
        return currentToken;
    }

    public void next(){
        currentTokenIndex++;
    }

    public String getLocation(){
        return "position " + currentTokenIndex + ", line " + line;
    }

    private static boolean isAllowedEnglishLetter(char c){
        return c >= 'a' && c <= 'z' || c == 'E';
    }

    private static boolean canCombineWithEscapeChar(char c){
        return c == '"' || c == '\\' || c == '/' || c == 'b' || c == 'f' || c == 'n' || c == 'r' || c == 't' || c == 'u';
    }

    private static boolean isNumber(char c){
        return c >= '0' && c <= '9';
    }

    private static boolean isHexNum(char c){
        return c >= 'a' && c <= 'f';
    }

    private static boolean allowedInfixInNumber(char c){
        return c == '.' || c == 'e' || c == 'E' || c == '+' || c == '-';
    }

    private static boolean isWhitespace(char c){
        return c == ' ' || c == '\n' || c == '\r' || c == '\t';
    }
}
