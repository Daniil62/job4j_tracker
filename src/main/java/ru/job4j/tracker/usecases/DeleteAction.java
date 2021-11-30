package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Tracker;

public class DeleteAction implements UserAction {

    @Override
    public String name() {
        return "Delete item";
    }

    @Override
    public boolean execute(Input input, Tracker tracker) {
        System.out.println(name());
        String id = input.askStr("Enter Id: ");
        if (tracker.delete(id)) {
            System.out.println("Data was successfully deleted.");
        } else {
            System.out.println("This Id do not exist!");
        }
        return true;
    }
}
