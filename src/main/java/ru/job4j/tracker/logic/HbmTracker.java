package ru.job4j.tracker.logic;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.tracker.model.Item;

import java.util.List;

public class HbmTracker implements Store {

    private final StandardServiceRegistry registry =
            new StandardServiceRegistryBuilder().configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void init() {
    }

    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    @Override
    public boolean replace(long id, Item item) {
        Session session = sf.openSession();
        item.setId(id);
        session.beginTransaction();
        boolean result = session.createQuery(
                "update Item i set i.name = :newName,"
                        + " i.description = :newDescription, i.created = :newCreated"
                        + " where id = :fId")
                .setParameter("fId", id)
                .setParameter("newName", item.getName())
                .setParameter("newDescription", item.getDescription())
                .setParameter("newCreated", item.getCreated())
                .executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public boolean delete(long id) {
        Session session = sf.openSession();
        session.beginTransaction();
        boolean result = session
                .createQuery("delete from Item where id = :fId")
                .setParameter("fId", id)
                .executeUpdate() > 0;
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public List<Item> findByName(String name) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session
                .createQuery("from Item where name = :fName")
                .setParameter("fName", name)
                .list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public Item findById(long id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}