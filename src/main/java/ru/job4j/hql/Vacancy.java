package ru.job4j.hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "vacansy")
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;

    public static Vacancy of(String description) {
        Vacancy vacancy = new Vacancy();
        vacancy.setDescription(description);
        return vacancy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vacancy vacancy = (Vacancy) o;
        return id == vacancy.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Vacancy{"
                + "id=" + id
                + ", description='" + description + '\''
                + '}';
    }
}
