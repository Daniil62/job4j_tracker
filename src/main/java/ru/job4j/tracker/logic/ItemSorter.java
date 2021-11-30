package ru.job4j.tracker.logic;

import ru.job4j.tracker.model.Item;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ItemSorter implements Comparator<Item> {

    public List<Item> incOrder(List<Item> items) {
        Collections.sort(items);
        return items;
    }

    public List<Item> decOrder(List<Item> items) {
        incOrder(items);
        Collections.reverse(items);
        return items;
    }

    @Override
    public int compare(Item first, Item second) {
        return first.getName().compareTo(second.getName());
    }

    public static void main(String[] args) {
        ItemSorter sorter = new ItemSorter();
        List<Item> items = Arrays.asList(
                new Item("item number 4"),
                new Item("item number 1"),
                new Item("item number 2"),
                new Item("item number 5"),
                new Item("item number 3"));
        System.out.println(items);
        sorter.incOrder(items);
        System.out.println(items);
        sorter.decOrder(items);
        System.out.println(items);
    }
}
