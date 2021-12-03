package ru.job4j.tracker.view;

import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.logic.SqlTracker;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.usecases.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StartUI {

    private static void showMenu(List<UserAction> actions) {
        AtomicInteger i = new AtomicInteger();
        actions.forEach(a -> System.out.println(String.format("%d %s", i.getAndIncrement(), a.name())));
    }

    public void init(Input input, Store tracker, List<UserAction> actions) {
        boolean run = true;
        while (run) {
            showMenu(actions);
            int select = input.askInt("Select: ", actions.size());
            run = actions.get(select).execute(input, tracker);
        }
    }

    public static void main(String[] args) {
        ArrayList<UserAction> actions = new ArrayList<>();
        actions.add(new CreateAction());
        actions.add(new ShowAllAction());
        actions.add(new ReplaceAction());
        actions.add(new DeleteAction());
        actions.add(new FindByIdAction());
        actions.add(new FindByNameAction());
        actions.add(new ExitAction());
        Input input = new ConsoleInput();
        Input validate = new ValidateInput(input);
        SqlTracker tracker = new SqlTracker();
        tracker.init();
        new StartUI().init(validate, tracker, actions);
    }
}
