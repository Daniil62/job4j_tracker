package ru.job4j.tracker.logic;

import ru.job4j.tracker.model.Item;

import java.util.List;

public interface Store extends AutoCloseable {

    void init();
    Item add(Item item);
    boolean replace(String id, Item item);
    boolean delete(String id);
    List<Item> findAll();
    List<Item> findByName(String name);
    Item findById(String id);
}
