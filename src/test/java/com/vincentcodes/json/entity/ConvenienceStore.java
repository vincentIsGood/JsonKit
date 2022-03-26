package com.vincentcodes.json.entity;

import com.vincentcodes.json.annotation.JsonSerializable;

import java.util.Arrays;

@JsonSerializable
public class ConvenienceStore {
    public Person manager;
    public Person[] staff;
    public String name;

    public String toString(){
        return String.format("{manager: %s, staff: %s, name: %s}", manager, Arrays.toString(staff), name);
    }
}
