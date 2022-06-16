package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.output.Output;

public class ExitAction implements UserAction {

    private final Output output;

    public ExitAction(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Exit";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.out(name());
        return false;
    }
}
