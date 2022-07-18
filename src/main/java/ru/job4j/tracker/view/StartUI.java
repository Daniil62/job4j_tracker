package ru.job4j.tracker.view;

import ru.job4j.tracker.input.ConsoleInput;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.ValidateInput;
import ru.job4j.tracker.logic.HbmTracker;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.output.ConsoleOutput;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.usecases.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StartUI {

    private static void showMenu(List<UserAction> actions) {
        AtomicInteger i = new AtomicInteger();
        actions.forEach(a -> System.out.println(String.format("%d %s", i.getAndIncrement(), a.name())));
        System.out.println();
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
        Output output = new ConsoleOutput();
        actions.add(new CreateAction(output));
        actions.add(new ShowAllAction(output));
        actions.add(new ReplaceAction(output));
        actions.add(new DeleteAction(output));
        actions.add(new FindByIdAction(output));
        actions.add(new FindByNameAction(output));
        actions.add(new ExitAction(output));
        Input input = new ConsoleInput();
        Input validate = new ValidateInput(input);
        Store tracker = new HbmTracker();
        tracker.init();
        new StartUI().init(validate, tracker, actions);
    }
}
