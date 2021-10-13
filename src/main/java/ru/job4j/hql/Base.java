package ru.job4j.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "base")
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public void addVacancy(Vacancy vacancy) {
        vacancies.add(vacancy);
    }

    public static Base of(String name) {
        Base base = new Base();
        base.setName(name);
        return base;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Base base = (Base) o;
        return id == base.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Base{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", books=" + vacancies
                + '}';
    }
}
