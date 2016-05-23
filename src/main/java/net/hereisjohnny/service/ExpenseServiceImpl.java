package net.hereisjohnny.service;

import net.hereisjohnny.dao.CategoryRepository;
import net.hereisjohnny.dao.ExpenseRepository;
import net.hereisjohnny.exceptions.CategoryNotFoundException;
import net.hereisjohnny.webservice.model.Category;
import net.hereisjohnny.webservice.model.Expense;
import net.hereisjohnny.webservice.rest.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by gomez on 5/22/16.
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {
    private static final Logger log = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    private final ExpenseRepository expenseRepository;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

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
        // Start the clock
        long start = System.currentTimeMillis();
        Expense expense = expenseRepository.findOne(id);

        Future<ProductResponse> responseFuture = null;
        ProductResponse response = null;
        try {
            responseFuture = productService.findProduct(String.valueOf(expense.getId()));
        } catch (InterruptedException ie) {

        }

        // Wait until all done
//        while (!responseFuture.isDone()) {
//            Thread.sleep(10);
//        }
        log.info("Elapsed time: " + (System.currentTimeMillis() - start));
        // Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
        try {
            response = responseFuture.get();
        } catch (ExecutionException ee) {

        } catch (InterruptedException ie) {

        }

        // Aggregate Product
        String description = response.getProductCompositeResponse().getItems().get(0).getOnlineDescription().getValue();
        Expense new_expense = new Expense(expense.getCategory(), description, expense.getCurrent_price());

        return new_expense;
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
    public ExpenseServiceImpl(ExpenseRepository expenseRepository, CategoryRepository categoryRepository, ProductService productService) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

}
