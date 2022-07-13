package ru.job4j.tracker.logic;

import ru.job4j.tracker.model.Item;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store {

    private Connection connection;
    private static final String PATH = "app.properties";

    public SqlTracker() {
    }

    public SqlTracker(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream(PATH)) {
            Properties config = new Properties();
            if (in != null) {
                config.load(in);
            }
            Class.forName(config.getProperty(Keys.DRIVER));
            connection = DriverManager.getConnection(config.getProperty(Keys.URL),
                    config.getProperty(Keys.USER), config.getProperty(Keys.PASSWORD)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into items(name, created, description) values(?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            statement.setString(3, item.getDescription());
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    item.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(long id, Item item) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(
                "update items set name = ?, created = ?, description = ? where id = ?")) {
            statement.setString(1, item.getName());
            statement.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            statement.setString(3, item.getDescription());
            statement.setLong(4, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(long id) {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from items where id = ?")) {
            statement.setLong(1, id);
            result = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from items")) {
            result = parseResultSetToItemsList(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findByName(String name) {
        List<Item> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from items where name = ?")) {
            statement.setString(1, name);
            result = parseResultSetToItemsList(statement.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Item findById(long id) {
        Item result = null;
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from items where id = ?")) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result = parseResultSetToItem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    private List<Item> parseResultSetToItemsList(ResultSet rs) {
        List<Item> result = new ArrayList<>();
        try {
            while (rs.next()) {
                result.add(parseResultSetToItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Item parseResultSetToItem(ResultSet rs) {
        Item result = null;
        try {
            result = new Item(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getTimestamp(3).toLocalDateTime(),
                    rs.getString(4));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static class Keys {
        static final String DRIVER = "driver-class-name";
        static final String URL = "url";
        static final String USER = "username";
        static final String PASSWORD = "password";
    }
}
