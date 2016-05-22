package net.hereisjohnny.webservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by gomez on 5/17/16.
 */
@Entity
public class Category implements Serializable{

    @OneToMany(mappedBy = "category")
    private Set<Expense> expenses = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Category(String name) {
        this.name = name;
    }
    // jpa only
    Category() {

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public Set<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
