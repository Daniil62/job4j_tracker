package ru.job4j.tracker.logic;

import ru.job4j.tracker.model.Item;

import java.util.ArrayList;
import java.util.List;

public class Tracker {
    private List<Item> items = new ArrayList<>();
    private String generateId() {
        java.util.Random rm = new java.util.Random();
        return String.valueOf(rm.nextLong() + System.currentTimeMillis());
    }
    private int indexOf(String id) {
        int result = -1;
        for (int i = 0; i < this.items.size(); ++i) {
            if (this.items.get(i).getId().equals(id)) {
                result = i;
                break;
            }
        }
        return result;
    }
    public Item add(Item item) {
        item.setId(this.generateId());
        this.items.add(item);
        return item;
    }
    public List<Item> findAll() {
        return this.items;
    }
    public List<Item> findByName(String key) {
        List<Item> itemsNameId = new ArrayList<>();
        for (Item item : this.items) {
            if (item.getName().equals(key)) {
                itemsNameId.add(item);
            }
        }
        return itemsNameId;
    }
    public Item findById(String id) {
        Item item = null;
        int index = indexOf(id);
        if (index != -1 && this.items.get(index).getId().equals(id)) {
            item = this.items.get(index);
        }
        return item;
    }
    public boolean replace(String id, Item item) {
        boolean result = false;
        int index = indexOf(id);
        if (index != -1) {
            item.setId(id);
            this.items.set(index, item);
            result = true;
        }
        return result;
    }
    public boolean delete(String id) {
        int index = indexOf(id);
        boolean result = false;
        if (index != -1) {
            this.items.remove(index);
            result = true;
        }
        return result;
    }
}
