package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.model.Item;

import java.util.List;

public class ShowAllAction implements UserAction {

    @Override
    public String name() {
        return "Show all items";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        System.out.println(name());
        List<Item> array = tracker.findAll();
        for (Item item : array) {
            System.out.println(System.lineSeparator() + item);
        }
        System.out.println(System.lineSeparator());
        return true;
    }
}
