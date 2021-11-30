package ru.job4j.tracker.model;

public class Item implements Comparable<Item> {

    private String id;
    private String name;

    public Item(String name) {
        this.name = name;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "Item: " + name;
    }
    @Override
    public int compareTo(Item o) {
        return this.name.compareTo(o.name);
    }
}
