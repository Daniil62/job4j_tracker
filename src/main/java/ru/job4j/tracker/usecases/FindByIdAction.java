package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.model.Item;

public class FindByIdAction implements UserAction {

    @Override
    public String name() {
        return "Find item by Id";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        System.out.println(name());
        String id = input.askStr("Enter Id: ");
        Item item = tracker.findById(id);
        if (item != null) {
            System.out.println(System.lineSeparator() + item + System.lineSeparator());
        } else {
            System.out.println(String.format("%sThis Id do not exist!%s",
                    System.lineSeparator(), System.lineSeparator()));
        }
        return true;
    }
}
