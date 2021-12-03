package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.model.Item;

public class ReplaceAction implements UserAction {

    @Override
    public String name() {
        return "Edit item";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        System.out.println(name());
        String id = input.askStr("Enter Id: ");
        Item item = new Item(input.askStr("Enter new name: "));
        if (tracker.replace(id, item)) {
            System.out.println(String.format("%sChanges made successfully.%s",
                    System.lineSeparator(), System.lineSeparator()));
        } else {
            System.out.println(String.format("%sThis Id do not exist!%s",
                    System.lineSeparator(), System.lineSeparator()));
        }
        return true;
    }
}
