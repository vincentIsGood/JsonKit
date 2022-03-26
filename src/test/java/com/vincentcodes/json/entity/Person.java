package com.vincentcodes.json.entity;

public class Person {
    public String name;
    public int age;
    public String desc;

    public Person(){}

    public Person(String name, int age, String desc) {
        this.name = name;
        this.age = age;
        this.desc = desc;
    }

    public String toString(){
        return String.format("Person{name: %s, age: %d, desc: %s}", name, age, desc);
    }
}
