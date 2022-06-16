package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;

import java.util.List;

public class FindByNameAction implements UserAction {

    private final Output output;

    public FindByNameAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Find items by name";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.out(name());
        String name = input.askStr("Enter name: ");
        List<Item> array = tracker.findByName(name);
        int value = array.size();
        for (Item item : array) {
            output.out(item.toString() + System.lineSeparator());
        }
        if (value == 0) {
            output.out("Nothing found." + System.lineSeparator());
        }
        return true;
    }
}
