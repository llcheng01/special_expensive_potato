package net.hereisjohnny.dao;

import net.hereisjohnny.webservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by gomez on 5/23/16.
 */
@Component
public class ProductRepository {

    private StringRedisTemplate stringRedisTemplate;

    public void save(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public String findById(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        return value;
    }

    @Autowired
    public ProductRepository(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
