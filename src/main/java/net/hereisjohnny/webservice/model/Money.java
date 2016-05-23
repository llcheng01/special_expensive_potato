package net.hereisjohnny.webservice.model;

import lombok.Data;

import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by gomez on 5/22/16.
 */
@Data
public class Money implements Comparable<Money>, Serializable{
    private BigDecimal value;
    private String currency_code;

    public Money(BigDecimal value, String currency_code) {
        this.value = value;
        this.currency_code = currency_code;
    }

    @Override
    public int compareTo(Money otherMoneyResource) {
        verifyCanCompareCurrency(otherMoneyResource);

        return this.value.compareTo(otherMoneyResource.value);
    }


    /**
     * Verify that we can compare ourselves against the provided
     * PricingResource. We must have the same currency in order to be
     * comparable.
     *
     * @param otherPricing
     *            The pricing with which we wish to compare ourselves.
     */
    private void verifyCanCompareCurrency(Money otherPricing) {
        if (currency_code != null && !currency_code.equalsIgnoreCase(otherPricing.currency_code)) {
            throw new IllegalStateException("Money have different currencies: can't compare");
        }
    }
}
