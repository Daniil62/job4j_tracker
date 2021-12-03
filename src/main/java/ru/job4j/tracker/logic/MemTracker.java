package ru.job4j.tracker.logic;

import ru.job4j.tracker.model.Item;

import java.util.ArrayList;
import java.util.List;

public class MemTracker implements Store {

    private List<Item> items = new ArrayList<>();

    private String generateId() {
        java.util.Random rm = new java.util.Random();
        return String.valueOf(rm.nextLong() + System.currentTimeMillis());
    }

    private int indexOf(String id) {
        int result = -1;
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i).getId().equals(id)) {
                result = i;
                break;
            }
        }
        return result;
    }

    @Override
    public void init() {
    }

    @Override
    public Item add(Item item) {
        item.setId(generateId());
        items.add(item);
        return item;
    }

    @Override
    public List<Item> findAll() {
        return items;
    }

    public List<Item> findByName(String key) {
        List<Item> itemsNameId = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                itemsNameId.add(item);
            }
        }
        return itemsNameId;
    }

    @Override
    public Item findById(String id) {
        Item item = null;
        int index = indexOf(id);
        if (index != -1 && items.get(index).getId().equals(id)) {
            item = items.get(index);
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        boolean result = false;
        int index = indexOf(id);
        if (index != -1) {
            item.setId(id);
            items.set(index, item);
            result = true;
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        int index = indexOf(id);
        boolean result = false;
        if (index != -1) {
            items.remove(index);
            result = true;
        }
        return result;
    }

    @Override
    public void close() {
    }
}
