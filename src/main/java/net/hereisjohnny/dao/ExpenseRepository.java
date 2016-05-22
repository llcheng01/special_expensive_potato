package net.hereisjohnny.dao;

import net.hereisjohnny.webservice.model.Category;
import net.hereisjohnny.webservice.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by gomez on 5/17/16.
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<Expense> findById(long id);

    Collection<Expense> findByCategory(Category category);
}
