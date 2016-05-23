package net.hereisjohnny;

import net.hereisjohnny.service.ExpenseService;
import net.hereisjohnny.webservice.model.Category;
import net.hereisjohnny.webservice.model.Expense;
import net.hereisjohnny.webservice.model.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;

/**
 * Created by gomez on 5/17/16.
 */
@RestController
@RequestMapping("{categoryName}/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;

    @RequestMapping(method = RequestMethod.POST)
    ResponseEntity<?> add(@PathVariable String categoryName, @RequestBody Expense input) {

        Expense result = expenseService.updateExpense(new Expense(new Category(categoryName), input.getName(), new Money(input.getCurrent_price().getValue(), "USD")));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}/expenses/{id}").buildAndExpand(result.getCategory().getName(), result.getId()).toUri());
        // construct response header
        return new ResponseEntity<>(null, httpHeaders, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{expenseId}", method = RequestMethod.GET)
    Expense readExpense(@PathVariable String categoryName, @PathVariable Long expenseId) {
        Expense expense = expenseService.findExpenseById(expenseId);
        return expense;
    }

    @RequestMapping(method = RequestMethod.GET)
    Collection<Expense> readExpenseByCategory(@PathVariable String categoryName) {
        Collection<Expense> expenses = expenseService.findAllByCategoryName(categoryName);
        return expenses;
    }

    @Autowired
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
}
