package net.hereisjohnny.webservice.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by gomez on 5/23/16.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductResponse {
    @JsonProperty("product_composite_response")
    ProductCompositeResponse productCompositeResponse;

    public ProductResponse() {
    }

    public ProductCompositeResponse getProductCompositeResponse() {
        return productCompositeResponse;
    }

    public void setProductCompositeResponse(ProductCompositeResponse productCompositeResponse) {
        this.productCompositeResponse = productCompositeResponse;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "productCompositeResponse=" + productCompositeResponse.toString() +
                '}';
    }
}
