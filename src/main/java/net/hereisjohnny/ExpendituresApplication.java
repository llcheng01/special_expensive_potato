package net.hereisjohnny;

import net.hereisjohnny.dao.CategoryRepository;
import net.hereisjohnny.dao.ExpenseRepository;
import net.hereisjohnny.webservice.model.Category;
import net.hereisjohnny.webservice.model.Expense;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class ExpendituresApplication {

	CommandLineRunner init(CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
		return (evt) -> Arrays.asList("breakfast, lunch, dinner, gas".split(",")).forEach(
				c -> {
					Category category = categoryRepository.save(new Category(c));
					expenseRepository.save(new Expense(category, "test " + c, 10.00, false, LocalDate.now()));
				}
		);
	}

	public static void main(String[] args) {
		SpringApplication.run(ExpendituresApplication.class, args);
	}
}
