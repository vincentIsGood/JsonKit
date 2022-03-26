package com.vincentcodes.json.entity;

import com.vincentcodes.json.JsonArray;
import com.vincentcodes.json.JsonObject;
import com.vincentcodes.json.annotation.JsonSerializable;

import java.util.Arrays;

@JsonSerializable
public class ConvenienceStoreRaw {
    public JsonObject manager;
    public JsonArray staff;
    public String name;
}
