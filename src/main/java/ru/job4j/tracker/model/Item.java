package ru.job4j.tracker.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table(name = "items")
public class Item implements Comparable<Item> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description = "";
    private LocalDateTime created = LocalDateTime.now();

    public Item() {
    }

    public Item(String name) {
        this.name = name;
    }

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Item(String name, String description, LocalDateTime created) {
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public Item(long id, String name, LocalDateTime created, String description) {
        this.id = id;
        this.name = name;
        this.created = created;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    @Override
    public String toString() {
        String n = System.lineSeparator();
        return String.format("id: %s,%sname: %s,%sdescription: %s,%screated: %s",
                id, n,
                name, n,
                description, n,
                FORMATTER.format(created));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(id, item.id)
                && name.equals(item.name)
                && Objects.equals(created.withNano(0), item.created.withNano(0));
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result *= 31 + Objects.hashCode(id);
        result *= 31 + created.hashCode();
        return result;
    }

    @Override
    public int compareTo(Item o) {
        return name.compareTo(o.name);
    }
}
