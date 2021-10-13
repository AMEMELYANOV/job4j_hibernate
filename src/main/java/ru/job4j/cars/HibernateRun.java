package ru.job4j.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        final SessionFactory sf = new MetadataSources(registry)
                .buildMetadata().buildSessionFactory();

        final Session session = sf.openSession();
        try {
            session.beginTransaction();

            Engine engine1 = new Engine("CG13DE");
            Engine engine2 = new Engine("CGA3DE");
            Car car1 = new Car(engine1);
            Car car2 = new Car(engine2);
            session.save(car1);
            session.save(car2);

            Car car = session.get(Car.class, 1);
            Driver driver1 = new Driver("Alexander");
            Driver driver2 = new Driver("Nikolay");
            Driver driver3 = new Driver("Sergey");
            car.addDriver(driver1);
            car.addDriver(driver2);
            car.addDriver(driver3);

            session.update(car);

            Car car3 = session.get(Car.class, 1);
            System.out.println(car3.getDrivers());
            Car car4 = session.get(Car.class, 2);
            System.out.println(car4.getDrivers());
            session.getTransaction().commit();
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
            sf.close();
        }
    }
}

