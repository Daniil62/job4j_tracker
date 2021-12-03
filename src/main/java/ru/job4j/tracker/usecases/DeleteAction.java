package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;

public class DeleteAction implements UserAction {

    @Override
    public String name() {
        return "Delete item";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        System.out.println(name());
        String id = input.askStr("Enter Id: ");
        if (tracker.delete(id)) {
            System.out.println(String.format("%sData was successfully deleted.%s",
                    System.lineSeparator(), System.lineSeparator()));
        } else {
            System.out.println(String.format("%sThis Id do not exist!%s",
                    System.lineSeparator(), System.lineSeparator()));
        }
        return true;
    }
}
