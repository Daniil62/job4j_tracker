package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;

public class FindByIdAction implements UserAction {

    private final Output output;

    public FindByIdAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Find item by Id";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.out(name());
        long id = Long.parseLong(input.askStr("Enter Id: "));
        Item item = tracker.findById(id);
        if (item != null) {
            output.out(item.toString() + System.lineSeparator());
        } else {
            output.out("This Id do not exist!" + System.lineSeparator());
        }
        return true;
    }
}
