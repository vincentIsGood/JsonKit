package com.vincentcodes.json.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JishoNote {
    int type;
    int value;
    String note;

    public JishoNote() {}

    public int getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public String getNote() {
        return note;
    }

    public String toString(){
        return String.format("{type: %d, value: %d, note: %s}", type, value, note);
    }
}