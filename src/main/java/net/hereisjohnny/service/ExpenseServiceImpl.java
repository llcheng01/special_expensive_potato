package net.hereisjohnny.service;

import net.hereisjohnny.dao.CategoryRepository;
import net.hereisjohnny.dao.ExpenseRepository;
import net.hereisjohnny.exceptions.CategoryNotFoundException;
import net.hereisjohnny.webservice.model.Category;
import net.hereisjohnny.webservice.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by gomez on 5/22/16.
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Collection<Category> findAllCatorgies() {
        Collection<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Collection<Expense> findAllByCategoryName(String name) {
        Collection<Expense> expenses = new ArrayList<>();
        Optional<Category> category = readCategory(name);
        if (!category.isPresent()) {
            throw new CategoryNotFoundException(name);
        } else {
            expenses = expenseRepository.findByCategory(category.get());
        }
        // return empty list if no items
        return expenses;
    }

    @Override
    public Expense findExpenseById(Long id) {
        Expense expense = expenseRepository.findOne(id);
        return expense;
    }

    @Override
    public Expense updateExpense(Expense expense) {
        Optional<Category> category = readCategory(expense.getCategory().getName());
        if (!category.isPresent()) {
            // Create new category
            categoryRepository.saveAndFlush(new Category(expense.getCategory().getName()));
        }
        Expense new_expense = expenseRepository.save(expense);

        return new_expense;
    }

    @Override
    public Optional<Category> readCategory(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        return category;
    }

    @Autowired
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

}
