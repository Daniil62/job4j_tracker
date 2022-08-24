package ru.job4j.tracker.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "items")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Item implements Comparable<Item> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm:ss");
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;
    @EqualsAndHashCode.Include
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
    public int compareTo(Item o) {
        return name.compareTo(o.name);
    }
}