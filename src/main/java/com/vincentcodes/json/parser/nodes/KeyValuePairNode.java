package com.vincentcodes.json.parser.nodes;

public class KeyValuePairNode extends Node {
    /**
     * @param left A NODE that contains a STRING token stored in Node.value variable
     * @param right node in general
     */
    public KeyValuePairNode(Node left, Node right) {
        super(left, right);
    }

    public String toString(){
        return String.format("{k: %s, v: %s}", left, right);
    }
}
