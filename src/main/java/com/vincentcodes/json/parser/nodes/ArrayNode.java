package com.vincentcodes.json.parser.nodes;

import java.util.ArrayList;

/**
 * values will be added into the ArrayList
 *
 * value:
 *         "<object>" OR
 *         "<array>" OR
 *         "<string>" OR
 *         "<number>" OR
 *         "true" OR
 *         "false" OR
 *         "null" OR
 */
public class ArrayNode extends Node {
    public ArrayNode(ArrayList<Node> value) {
        super(value);
    }
}
