package ru.job4j.tracker.usecases;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.logic.SqlTracker;
import ru.job4j.tracker.logic.SqlTrackerTest;
import ru.job4j.tracker.logic.Store;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateActionTest {

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
    public void whenItemCreated() {
        Output out = new StubOutput();
        String name = "New";
        UserAction action = new CreateAction(out);
        Input input = mock(Input.class);
        when(input.askStr(any(String.class))).thenReturn(name);
        action.execute(input, tracker);
        String ln = System.lineSeparator();
        assertThat(out.toString(), is("Add new Item" + ln + "Item " + name + " added." + ln + ln));
        assertThat(tracker.findAll().get(0).getName(), is(name));
    }
}
