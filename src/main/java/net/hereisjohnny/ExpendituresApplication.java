package net.hereisjohnny;

import net.hereisjohnny.dao.CategoryRepository;
import net.hereisjohnny.dao.ExpenseRepository;
import net.hereisjohnny.webservice.model.Category;
import net.hereisjohnny.webservice.model.Expense;
import net.hereisjohnny.webservice.model.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@SpringBootApplication
public class ExpendituresApplication {

	private static final Logger log = LoggerFactory.getLogger(ExpendituresApplication.class);

	@Bean
	CommandLineRunner init(CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
		return (evt) -> {

			Category category1 = categoryRepository.save(new Category("breakfast"));
			Category category2 = categoryRepository.save(new Category("lunch"));
			Category category3 = categoryRepository.save(new Category("dinner"));
			Category category4 = categoryRepository.save(new Category("gas"));

			// Breakfast
			expenseRepository.save(new Expense(category1, "Dennys cafe", new Money(new BigDecimal("10.0"), "USD")));
			expenseRepository.save(new Expense(category2, "home", new Money(new BigDecimal("5.0"), "USD")));
			expenseRepository.save(new Expense(category3, "Lee Sandwiches", new Money(new BigDecimal("15.0"), "USD")));
			expenseRepository.save(new Expense(category4, "Gas 76 station", new Money(new BigDecimal("25.0"), "USD")));

			log.info("Categories found with findAll():");
			log.info("-------------------------------");
			for (Category category : categoryRepository.findAll()) {
				log.info(category.toString());
			}
			log.info("");


			log.info("Expenses found with findAll():");
			log.info("-------------------------------");
			for (Expense expense : expenseRepository.findAll()) {
				log.info(expense.toString());
			}
			log.info("");

			// fetch an individual expense by ID
			Expense expense2 = expenseRepository.findOne(2L);
			log.info("Expense found with findOne(2L):");
			log.info("--------------------------------");
			log.info(expense2.toString());
			log.info("");

		};
	}


	public static void main(String[] args) {
		SpringApplication.run(ExpendituresApplication.class, args);
	}
}
