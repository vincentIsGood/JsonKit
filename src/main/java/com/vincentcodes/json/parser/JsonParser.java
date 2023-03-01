/**
 * <pre>{@code
 * [,~] => optional
 * ~ => loop or repeating of the same element
 *
 * // higher level (will be done by a parser)
 * object:
 *        "{<string>:<value>[,~]}"
 *
 * value:
 *        "<object>" OR
 *        "<array>" OR
 *        "<string>" OR
 *        "<number>" OR
 *        "true" OR
 *        "false" OR
 *        "null" OR
 *
 * array:
 *        "[<value>[,~]]"
 *
 * string:
 *        control characters (prepended with "\" escape character):
 *            " \ / b f n r t u0000
 *
 * number:
 *        "+/-<number>" OR
 *        "<digit>.<digit>" OR
 *        "<digit>e/E[-/+]<digit>" OR
 *
 * whitespace:
 *        "\s" OR "\n" OR "\r" OR "\t" OR "" (nothing)
 * 
 * }</pre>
 * @see https://www.json.org/json-en.html
 */

package com.vincentcodes.json.parser;

import com.vincentcodes.json.lexer.JsonLexer;
import com.vincentcodes.json.lexer.Token;
import com.vincentcodes.json.lexer.TokenTypes;
import com.vincentcodes.json.parser.nodes.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A json parser. The gist of the whole project.
 * Performance is still not yet tested. Maybe conduct
 * a test on it? I'm not confident though.
 *
 * @author vincent ko
 */
public class JsonParser {
    private JsonLexer lexer;

    public JsonParser(){}

    /**
     * To understand what object maybe, see {@link NodeToObjectConverter}
     */
    public ArrayList<Object> parseJsonArray(String json) throws UnexpectedToken{
        this.lexer = new JsonLexer(json);
        if(lexer.getCurrentToken() == null){
            lexer.nextToken();
        }
        return NodeToObjectConverter.nodeToJavaArray(array());
        //throw new UnexpectedToken("The json must start with TokenTypes.OPEN_SQUARE_BRACKET for JsonParser.parseJsonArray(String)");
    }

    /**
     * To understand what object maybe, see {@link NodeToObjectConverter}
     */
    public HashMap<String, Object> parseJson(String json) throws UnexpectedToken{
        this.lexer = new JsonLexer(json);
        if(lexer.getCurrentToken() == null){
            lexer.nextToken();
        }
        // or an object (classic)
        return NodeToObjectConverter.nodeToJavaObject(object());
    }

    // ------------------ Low Level Parsing ------------------ //
    private Node number() throws UnexpectedToken{
        Token currentToken = lexer.getCurrentToken();
        if(currentToken.type == TokenTypes.PLUS || currentToken.type == TokenTypes.MINUS){
            lexer.nextToken();
            return new NumberNode(Double.parseDouble(currentToken.value + number().value));
        }else if(currentToken.type == TokenTypes.NUMBER){
            lexer.nextToken();
            return new NumberNode(Double.parseDouble(currentToken.value));
        }
        throw new UnexpectedToken("A number is expected at " + lexer.getLocation());
    }

    private Node value() throws UnexpectedToken{
        Token currentToken = lexer.getCurrentToken();
        if(currentToken.type == TokenTypes.OPEN_CURLY_BRACKET){
            return object();
        }else if(currentToken.type == TokenTypes.OPEN_SQUARE_BRACKET){
            return array();
        }else if(currentToken.type == TokenTypes.STRING){
            lexer.nextToken();
            return new StringNode(currentToken.value);
        }else if(currentToken.type == TokenTypes.NUMBER || currentToken.type == TokenTypes.PLUS || currentToken.type == TokenTypes.MINUS){
            return number();
        }else if(currentToken.type == TokenTypes.LETTER){
            if(currentToken.value.equals("true") || currentToken.value.equals("false") || currentToken.value.equals("null")){
                lexer.nextToken();
                return new Node(currentToken.value);
            }
            throw new InvalidValue("'" + currentToken.value + "' cannot be used as value at " + lexer.getLocation());
        }
        throw new UnexpectedToken("A value is expected, instead of '"+ currentToken.value +"', at " + lexer.getLocation());
    }

    private Node array() throws UnexpectedToken{
        Token currentToken = lexer.getCurrentToken();
        if(currentToken.type == TokenTypes.OPEN_SQUARE_BRACKET){
            ArrayList<Node> arrayList = new ArrayList<>();
            ArrayNode arrayNode = new ArrayNode(arrayList);
            Token nextToken = lexer.nextToken();
            if(nextToken.type == TokenTypes.CLOSE_SQUARE_BRACKET){
                // Special case for []. Skip "]". Since it won't pass through the while loop
                lexer.nextToken();
            }
            while(nextToken.type != TokenTypes.CLOSE_SQUARE_BRACKET){
                arrayList.add(value());
                nextToken = lexer.getCurrentToken();
                if(nextToken.type == TokenTypes.CLOSE_SQUARE_BRACKET){
                    lexer.nextToken();
                    break;
                }else if(nextToken.type == TokenTypes.COMMA){
                    nextToken = lexer.nextToken();
                    continue;
                }else
                    throw new UnexpectedToken("'" + nextToken.value + "' is not a valid token for Array at " + lexer.getLocation());
            }
            return arrayNode;
        }
        throw new UnexpectedToken("'[' is expected, instead of '"+ currentToken.value +"', at " + lexer.getLocation());
    }

    private Node object() throws UnexpectedToken{
        Token currentToken = lexer.getCurrentToken();
        if(currentToken.type == TokenTypes.OPEN_CURLY_BRACKET){
            ArrayList<Node> jsonProps = new ArrayList<>();
            ObjectNode objectNode = new ObjectNode(jsonProps);
            Token nextToken = lexer.nextToken();
            while(nextToken.type != TokenTypes.CLOSE_CURLY_BRACKET){
                if(nextToken.type == TokenTypes.STRING) {
                    KeyValuePairNode kv = new KeyValuePairNode(new StringNode(nextToken.value), null);
                    nextToken = lexer.nextToken();
                    if(nextToken.type == TokenTypes.COLON){
                        lexer.nextToken();
                        kv.right = value();
                        nextToken = lexer.getCurrentToken();
                        jsonProps.add(kv);
                        if (lexer.getCurrentToken().type == TokenTypes.COMMA) {
                            nextToken = lexer.nextToken();
                        }
                    }else {
                        throw new UnexpectedToken("':' is expected at " + lexer.getLocation());
                    }
                }else {
                    throw new UnexpectedToken("A string is expected at " + lexer.getLocation());
                }
            }
            lexer.nextToken();
            return objectNode;
        }
        throw new UnexpectedToken("'{' is expected, instead of '"+ currentToken.value +"', at " + lexer.getLocation());
    }
}
