package net.hereisjohnny.webservice.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Currency;

/**
 * Created by gomez on 5/17/16.
 */

@Entity
public class Expense implements Serializable{

    @ManyToOne
    private Category category;

    @Id
    @GeneratedValue
    private long id;
    private String name;
    @Column(length = 100000)
    private Money current_price;

    public Expense(Category category, String name, Money amount) {
        this.category = category;
        this.name = name;
        this.current_price = amount;
    }

    public Category getCategory() {
        return category;
    }

    public long getId() {
        return id;
    }

    public Money getCurrent_price() {
        return current_price;
    }

    public String getName() {
        return name;
    }

    // jpa only
    Expense() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Expense expense = (Expense) o;

        if (id != expense.id) return false;
        if (category != null ? !category.equals(expense.category) : expense.category != null) return false;
        if (!name.equals(expense.name)) return false;
        return current_price.equals(expense.current_price);

    }

    @Override
    public int hashCode() {
        int result = category != null ? category.hashCode() : 0;
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + current_price.hashCode();
        return result;
    }
}
