package ru.job4j.tracker.logic;

import org.junit.Test;
import ru.job4j.tracker.model.Item;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class HbmTrackerTest {

    @Test
    public void whenItemAdded() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("Name", "Description");
        assertTrue(tracker.add(item).getId() > 0);
    }

    @Test
    public void whenReplace() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("new item", "Description");
        item = tracker.add(item);
        item.setName("New Item");
        assertTrue(tracker.replace(item.getId(), item));
        assertThat(tracker.findAll().get(0).getName(), is("New Item"));
    }

    @Test
    public void whenReplaceWithInvalidId() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("new item", "Description");
        item = tracker.add(item);
        item.setName("New Item");
        assertFalse(tracker.replace(0, item));
        assertThat(tracker.findAll().get(0).getName(), is("new item"));
    }

    @Test
    public void whenDelete() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("Some Item", "Unnecessary");
        item = tracker.add(item);
        assertThat(tracker.findAll(), is(List.of(item)));
        assertTrue(tracker.delete(item.getId()));
        assertThat(tracker.findAll(), is(List.of()));
    }

    @Test
    public void whenDeleteWithInvalidId() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("Some Item", "Unnecessary");
        tracker.add(item);
        assertThat(tracker.findAll(), is(List.of(item)));
        assertFalse(tracker.delete(0));
        assertThat(tracker.findAll(), is(List.of(item)));
    }

    @Test
    public void whenItemsAddedThenFoundAll() {
        HbmTracker tracker = new HbmTracker();
        Item first = new Item("First", "First");
        Item second = new Item("Second", "Second");
        Item third = new Item("Third", "Third");
        first = tracker.add(first);
        second = tracker.add(second);
        third = tracker.add(third);
        List<Item> items = tracker.findAll();
        assertThat(items.size(), is(3));
        assertThat(items, is(List.of(first, second, third)));
    }

    @Test
    public void whenFindByName() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("Name", "Description");
        item = tracker.add(item);
        assertThat(tracker.findByName("Name"), is(List.of(item)));
    }

    @Test
    public void whenFindWithInvalidName() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("Name", "Description");
        tracker.add(item);
        assertThat(tracker.findByName("___"), is(List.of()));
    }

    @Test
    public void whenFindById() {
        HbmTracker tracker = new HbmTracker();
        Item first = new Item("First", "First");
        Item second = new Item("Second", "Second");
        first = tracker.add(first);
        second = tracker.add(second);
        assertThat(tracker.findById(first.getId()), is(first));
        assertThat(tracker.findById(second.getId()), is(second));
    }

    @Test
    public void whenFindWithId() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("Name", "Description");
        tracker.add(item);
        assertNull(tracker.findById(0));
    }
}
