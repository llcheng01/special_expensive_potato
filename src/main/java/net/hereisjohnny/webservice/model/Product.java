package net.hereisjohnny.webservice.model;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by gomez on 5/23/16.
 */
public class Product implements Serializable {
    @Id
    private long id;

    @Column(length = 100000)
    private Money current_price;

    public Product(long id, Money current_price) {
        this.id = id;
        this.current_price = current_price;
    }

    public long getId() {
        return id;
    }

    public Money getCurrent_price() {
        return current_price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        return current_price != null ? current_price.equals(product.current_price) : product.current_price == null;

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (current_price != null ? current_price.hashCode() : 0);
        return result;
    }
}
