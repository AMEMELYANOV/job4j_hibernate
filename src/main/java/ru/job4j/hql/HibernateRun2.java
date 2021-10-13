package ru.job4j.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.management.Query;
import java.util.List;

public class HibernateRun2 {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();

        List<Candidate> candidateList = null;
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Base base1 = Base.of("SQL.ru");
            Base base2 = Base.of("hh.ru");

            Candidate one = Candidate.of("Alex", "Top", 1500, base1);
            Candidate two = Candidate.of("Nikolay", "Middle", 1000, base1);
            Candidate three = Candidate.of("Nikita", "Junior", 800, base2);

            session.save(one);
            session.save(two);
            session.save(three);

            one.getBase().addVacancy(Vacancy.of("Java Junior"));
            one.getBase().addVacancy(Vacancy.of("Java Middle"));
            two.getBase().addVacancy(Vacancy.of("Python Junior"));
            three.getBase().addVacancy(Vacancy.of("Python Middle"));

            session.update(one);
            session.update(two);
            session.update(three);

            Candidate candidate = session.createQuery(
                    "select distinct c from Candidate c "
                            + "join fetch c.base b "
                            + "join fetch b.vacancies v "
                            + "where c.id = :cId", Candidate.class
            ).setParameter("cId", 1).uniqueResult();
            System.out.println(candidate);

            candidateList = session.createQuery(
                    "select distinct c from Candidate c "
                    + "join fetch c.base b "
                    + "join fetch b.vacancies v ",
                    Candidate.class).getResultList();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Candidate candidate : candidateList) {
            System.out.println(candidate);
        }
    }
}
