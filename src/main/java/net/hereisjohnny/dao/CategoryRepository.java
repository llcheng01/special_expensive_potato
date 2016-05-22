package net.hereisjohnny.dao;

import net.hereisjohnny.webservice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by gomez on 5/17/16.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
