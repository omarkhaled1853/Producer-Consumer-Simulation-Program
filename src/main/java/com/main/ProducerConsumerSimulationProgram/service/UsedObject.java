package com.main.ProducerConsumerSimulationProgram.service;

public class UsedObject {
    private String name, color;
    public UsedObject(String name, String color){
        this.name = name;
        this.color =color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "UsedObject{" +
                "name='" + name + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
