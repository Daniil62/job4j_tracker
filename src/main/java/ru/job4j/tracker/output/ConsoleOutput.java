package ru.job4j.tracker.output;

public class ConsoleOutput implements Output {

    @Override
    public void out(String text) {
        System.out.println(text);
    }
}
