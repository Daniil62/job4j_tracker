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
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;

public class ExitActionTest {

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
    public void whenProgramFinished() {
        Output out = new StubOutput();
        UserAction action = new ExitAction(out);
        Input input = mock(Input.class);
        String ln = System.lineSeparator();
        assertFalse(action.execute(input, tracker));
        assertThat(out.toString(), is("Exit" + ln));
    }
}
