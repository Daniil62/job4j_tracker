package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;

public class ReplaceAction implements UserAction {

    private final Output output;

    public ReplaceAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Edit item";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.out(name());
        long id = Long.parseLong(input.askStr("Enter Id: "));
        Item item = new Item(input.askStr("Enter new name: "));
        if (tracker.replace(id, item)) {
            output.out("Changes made successfully." + System.lineSeparator());
        } else {
            output.out("This Id do not exist!" + System.lineSeparator());
        }
        return true;
    }
}
