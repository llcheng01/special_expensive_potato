package net.hereisjohnny.webservice.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

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
    private String title;
    private double amount;
    private boolean pay_with_visa;
    private LocalDate posted_at;

    public Expense(Category category, String title, double amount, boolean pay_with_visa, LocalDate posted_at) {
        this.title = title;
        this.category = category;
        this.amount = amount;
        this.pay_with_visa = pay_with_visa;
        this.posted_at = posted_at;
    }

    public Category getCategory() {
        return category;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isPay_with_visa() {
        return pay_with_visa;
    }

    public LocalDate getPosted_at() {
        return posted_at;
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
        if (Double.compare(expense.amount, amount) != 0) return false;
        if (pay_with_visa != expense.pay_with_visa) return false;
        if (!category.equals(expense.category)) return false;
        if (!title.equals(expense.title)) return false;
        return posted_at.equals(expense.posted_at);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = category.hashCode();
        result = 31 * result + (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (pay_with_visa ? 1 : 0);
        result = 31 * result + posted_at.hashCode();
        return result;
    }
}
