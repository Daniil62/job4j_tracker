package ru.job4j.tracker.output;

public class StubOutput implements Output {

    private StringBuilder builder = new StringBuilder();

    @Override
    public void out(String text) {
        builder.append(text).append(System.lineSeparator());
    }

    @Override
    public String toString() {
        String result = builder.toString();
        builder.delete(0, builder.length());
        return result;
    }
}
