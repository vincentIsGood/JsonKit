package com.vincentcodes.json.parser.nodes;

import com.vincentcodes.json.parser.nodes.Node;

/**
 * Uses double, which means long is not supported YET.
 */
public class NumberNode extends Node {
    public NumberNode(double value){
        super(value);
    }
}
