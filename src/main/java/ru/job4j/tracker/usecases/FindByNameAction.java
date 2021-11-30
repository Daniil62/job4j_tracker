package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Tracker;
import ru.job4j.tracker.model.Item;

import java.util.List;

public class FindByNameAction implements UserAction {

    @Override
    public String name() {
        return "Find items by name";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        System.out.println(name());
        String name = input.askStr("Enter name: ");
        List<Item> array = tracker.findByName(name);
        int value = array.size();
        for (Item item : array) {
            System.out.println(item);
        }
        if (value == 0) {
            System.out.println("   Nothing found.");
        }
        return true;
    }
}
