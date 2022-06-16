package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;

import java.util.List;

public class ShowAllAction implements UserAction {

    private final Output output;

    public ShowAllAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Show all items";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.out(name());
        List<Item> array = tracker.findAll();
        for (Item item : array) {
            output.out(item.toString() + System.lineSeparator());
        }
        return true;
    }
}
