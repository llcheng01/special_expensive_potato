package net.hereisjohnny;

import net.hereisjohnny.dao.CategoryRepository;
import net.hereisjohnny.dao.ExpenseRepository;
import net.hereisjohnny.exceptions.CategoryNotFoundException;
import net.hereisjohnny.webservice.model.Category;
import net.hereisjohnny.webservice.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by gomez on 5/17/16.
 */
@RestController
@RequestMapping("{categoryName}/expenses")
public class ExpenseController {
    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;


    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String categoryName, @RequestBody Expense input) {
        return this.categoryRepository.findByName(categoryName).map(category -> {
            Expense result = expenseRepository.save(new Expense(category, input.getTitle(), input.getAmount(), input.isPay_with_visa(), input.getPosted_at()));
            // construct response header

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(result.getId()).toUri());
            return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);

        }).get();
    }

    @RequestMapping(value = "/{expenseId}", method = RequestMethod.GET)
    Expense readExpense(@PathVariable String categoryName, @PathVariable Long expenseId) {
        Expense expense = expenseRepository.findOne(expenseId);
        return expense;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Expense> readExpenseByCategory(@PathVariable String categoryName) {
        Category category = readCategory(categoryName);
        Collection<Expense> expenses = expenseRepository.findByCategory(category);
        return expenses;
    }

    @Autowired
    public ExpenseController(ExpenseRepository expenseRepository, CategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    private Category readCategory(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        if (!category.isPresent()) {
            throw new CategoryNotFoundException(name);
        }
        return category.get();
    }
}
