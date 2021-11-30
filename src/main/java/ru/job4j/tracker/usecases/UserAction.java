package ru.job4j.tracker.usecases;

import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.Tracker;

public interface UserAction {
    String name();
    boolean execute(Input input, Tracker tracker);
}
