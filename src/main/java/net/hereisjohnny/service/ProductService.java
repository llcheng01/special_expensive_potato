package net.hereisjohnny.service;

import net.hereisjohnny.webservice.rest.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * Created by gomez on 5/23/16.
 */
@Service
public class ProductService {
    private static final String TARGET_API_ROOT = "https://api.target.com/products/v3/";
    private static final String TARGET_API_ATTRIBUTES = "?fields=descriptions&id_type=TCIN&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz";
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    RestTemplate restTemplate = new RestTemplate();

    @Async
    public Future<ProductResponse> findProduct(String product_id) throws InterruptedException {
        log.info("Call Target API");
        log.info("--------------------------------");
        ProductResponse response = restTemplate.getForObject(TARGET_API_ROOT + product_id + TARGET_API_ATTRIBUTES, ProductResponse.class);

        Thread.sleep(1000L);
        return new AsyncResult<ProductResponse>(response);
    }
}
