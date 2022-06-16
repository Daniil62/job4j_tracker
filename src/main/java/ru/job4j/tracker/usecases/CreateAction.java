package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;

public class CreateAction implements UserAction {

    private final Output output;

    public CreateAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Add new Item";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.out(name());
        String name = input.askStr("Enter name: ");
        Item item = new Item(name);
        tracker.add(item);
        output.out(String.format("Item %s added.%s", item.getName(), System.lineSeparator()));
        return true;
    }
}
