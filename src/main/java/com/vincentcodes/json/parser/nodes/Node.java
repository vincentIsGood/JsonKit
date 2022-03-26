package com.vincentcodes.json.parser.nodes;

public class Node {
    public Node left;
    public Node right;
    public Object value;

    public Node(Node left, Node right) {
        this.left = left;
        this.right = right;
    }

    public Node(Object value) {
        this.value = value;
    }

    public String toString(){
        return String.format("{%s}", value.toString());
    }
}
