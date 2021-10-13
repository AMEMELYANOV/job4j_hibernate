package ru.job4j.hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String experience;
    private int salary;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Base base;

 public static Candidate of(String name, String experience, int salary) {
     Candidate candidate = new Candidate();
     candidate.name = name;
     candidate.experience = experience;
     candidate.salary = salary;
     return candidate;
 }

 public static Candidate of(String name, String experience, int salary, Base base) {
     Candidate candidate = new Candidate();
     candidate.name = name;
     candidate.experience = experience;
     candidate.salary = salary;
     candidate.base = base;
     return candidate;
 }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Candidate: id=%s, name=%s, experience=%s, salary=%s, base=%s",
                id, name, experience, salary, base);
    }
}