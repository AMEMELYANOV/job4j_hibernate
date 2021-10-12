package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HibernateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate one = Candidate.of("Alex", "Top", 1500);
            Candidate two = Candidate.of("Nikolay", "Middle", 1000);
            Candidate three = Candidate.of("Nikita", "Junior", 800);

            session.save(one);
            session.save(two);
            session.save(three);

            Query query = session.createQuery("from Candidate");
            for (Object can : query.list()) {
                System.out.println(can);
            }

            query = session.createQuery("from Candidate c where c.id = 2");
            System.out.println(query.uniqueResult());

            query = session.createQuery("from Candidate c where c.name = :name");
            query.setParameter("name", "Nikita");
            System.out.println(query.list());

            session.createQuery("update Candidate c set c.salary = :newSalary, "
                    + "c.experience = :newExperience where c.id = :fId")
                    .setParameter("newSalary", 900)
                    .setParameter("newExperience", "Middle")
                    .setParameter("fId", 3)
                    .executeUpdate();

            session.createQuery("delete from Candidate where id = :fId")
                    .setParameter("fId", 1)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
