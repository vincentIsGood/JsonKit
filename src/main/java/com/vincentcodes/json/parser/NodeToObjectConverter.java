package com.vincentcodes.json.parser;

import com.vincentcodes.json.parser.nodes.*;

import java.util.ArrayList;
import java.util.HashMap;

public class NodeToObjectConverter {
    public static HashMap<String, Object> nodeToJavaObject(Node node){
        if(!(node instanceof ObjectNode))
            throw new ConversionException("Input node must be an ObjectNode");

        ObjectNode objectNode = (ObjectNode)node;

        // An ObjectNode must be initialized with an ArrayList<Node>
        @SuppressWarnings("unchecked")
        ArrayList<Node> propertiesArray = (ArrayList<Node>)objectNode.value;

        HashMap<String, Object> properties = new HashMap<>();
        for(Node tmpNode : propertiesArray) {
            KeyValuePairNode kvNode = (KeyValuePairNode) tmpNode;
            Node value = kvNode.right;
            properties.put((String) kvNode.left.value, nodeToJavaValues(value));
        }
        return properties;
    }

    public static Object nodeToJavaValues(Node node){
            if(node instanceof ObjectNode){
                return nodeToJavaObject(node);
            }else if(node instanceof ArrayNode){
                return nodeToJavaArray(node);
            }else if(node instanceof StringNode){
                return (String)node.value;
            }else if(node instanceof NumberNode){
                return (Double)node.value;
            }else if(node.value.equals("true")){
                return true;
            }else if(node.value.equals("false")){
                return false;
            }else if(node.value.equals("null")){
                return null;
            }
        throw new ConversionException(node + " is not an acceptable node");
    }

    public static ArrayList<Object> nodeToJavaArray(Node node){
        if(!(node instanceof ArrayNode))
            throw new ConversionException("Input node must be an ArrayNode");

        ArrayNode arrayNode = (ArrayNode)node;

        // An ObjectNode must be initialized with an ArrayList<Node>
        @SuppressWarnings("unchecked")
        ArrayList<Node> arrayList = (ArrayList<Node>)arrayNode.value;

        ArrayList<Object> elements = new ArrayList<>();
        for(Node tmpNode : arrayList){
            elements.add(nodeToJavaValues(tmpNode));
        }
        return elements;
    }
}
