package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.output.Output;

public class DeleteAction implements UserAction {

    private final Output output;

    public DeleteAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Delete item";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.out(name());
        long id = Long.parseLong(input.askStr("Enter Id: "));
        if (tracker.delete(id)) {
            output.out("Data was successfully deleted." + System.lineSeparator());
        } else {
            output.out("This Id do not exist!" + System.lineSeparator());
        }
        return true;
    }
}
