package net.hereisjohnny.service;

import net.hereisjohnny.webservice.model.Category;
import net.hereisjohnny.webservice.model.Expense;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by gomez on 5/22/16.
 */
public interface ExpenseService {
    Collection<Category> findAllCatorgies();
    Collection<Expense> findAllByCategoryName(String name);
    Expense findExpenseById(Long id);
    Expense updateExpense(Expense expense);
    Optional<Category> readCategory(String name);
}
