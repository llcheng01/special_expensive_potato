package net.hereisjohnny.webservice.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Set;

/**
 * Created by gomez on 5/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductCompositeResponse {
    @JsonProperty("items")
    List<Item> items;

    public ProductCompositeResponse() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {

        return "ProductCompositeResponse{" +
                "items=" + items.get(0).toString() +
                '}';
    }
}
