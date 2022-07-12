package ru.job4j.tracker.usecases;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.SqlTracker;
import ru.job4j.tracker.logic.SqlTrackerTest;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.model.Item;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteActionTest {

    static Connection connection;
    private static Store tracker;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = SqlTrackerTest.class
                .getClassLoader().getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            assert in != null;
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
            tracker = new SqlTracker(connection);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    public void whenItemDeleted() {
        Output out = new StubOutput();
        Item item = new Item("Unnecessary");
        tracker.add(item);
        UserAction action = new DeleteAction(out);
        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn(String.valueOf(item.getId()));
        action.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is("Delete item" + ln + "Data was successfully deleted." + ln + ln));
        assertThat(tracker.findAll(), is(List.of()));
    }

    @Test
    public void whenIdInvalid() {
        Output out = new StubOutput();
        Item item = new Item("Lucky_item");
        tracker.add(item);
        UserAction action = new DeleteAction(out);
        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn("-1");
        action.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is("Delete item" + ln + "This Id do not exist!" + ln + ln));
        assertThat(tracker.findAll(), is(List.of(item)));
    }
}
