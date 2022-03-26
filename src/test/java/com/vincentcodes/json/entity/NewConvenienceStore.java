package com.vincentcodes.json.entity;

import java.util.List;

public class NewConvenienceStore {
    public Person manager;
    public List<Person> staff;
    public String name;

    public String toString(){
        return String.format("{manager: %s, staff: %s, name: %s}", manager, staff.toString(), name);
    }
}
