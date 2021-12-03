package ru.job4j.tracker.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class SqlTrackerTest {

    static Connection connection;
    static SqlTracker tracker;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = SqlTrackerTest.class.getClassLoader().getResourceAsStream("test.properties")) {
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

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void whenSaveItemThenReplace() {
        Item item = new Item("item");
        tracker.add(item);
        Item newItem = new Item("new item");
        tracker.replace(item.getId(), newItem);
        assertThat(tracker.findById(item.getId()).getName(), is(newItem.getName()));
    }

    @Test
    public void whenAddedThreeItemsThenFindAll() {
        Item item = new Item("item");
        Item item2 = new Item("item 2");
        Item item3 = new Item("item 3");
        tracker.add(item);
        tracker.add(item2);
        tracker.add(item3);
        assertThat(tracker.findAll(), is(List.of(item, item2, item3)));
    }

    @Test
    public void whenTwoItemsAddedAndOneDeleted() {
        Item item = new Item("item");
        Item item2 = new Item("item 2");
        tracker.add(item);
        tracker.add(item2);
        assertTrue(tracker.delete(item2.getId()));
        assertThat(tracker.findAll(), is(List.of(item)));
    }

    @Test
    public void whenAddedTwoItemsWithSameNamesThenAllFindByName() {
        Item item = new Item("item");
        Item item2 = new Item("item");
        tracker.add(item);
        tracker.add(item2);
        assertThat(tracker.findByName("item"), is(List.of(item, item2)));
    }

}
