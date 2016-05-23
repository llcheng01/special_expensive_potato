package net.hereisjohnny;

import net.hereisjohnny.dao.CategoryRepository;
import net.hereisjohnny.dao.ExpenseRepository;
import net.hereisjohnny.service.ProductService;
import net.hereisjohnny.webservice.model.Category;
import net.hereisjohnny.webservice.model.Expense;
import net.hereisjohnny.webservice.model.Money;
import net.hereisjohnny.webservice.rest.ProductCompositeResponse;
import net.hereisjohnny.webservice.rest.ProductResponse;
import net.hereisjohnny.webservice.rest.Quote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.Future;

@SpringBootApplication
public class ExpendituresApplication {

	private static final Logger log = LoggerFactory.getLogger(ExpendituresApplication.class);

	@Bean
	CommandLineRunner init(CategoryRepository categoryRepository, ExpenseRepository expenseRepository, ProductService productService) {
		return (evt) -> {

			Category category1 = categoryRepository.save(new Category("breakfast"));
			Category category2 = categoryRepository.save(new Category("lunch"));
			Category category3 = categoryRepository.save(new Category("dinner"));
			Category category4 = categoryRepository.save(new Category("gas"));
            Category category5 = categoryRepository.save(new Category("movies"));
            Category category6 = categoryRepository.save(new Category("electronics"));

			// Breakfast
			expenseRepository.save(new Expense(category1, Long.valueOf("1"), "Dennys cafe", new Money(new BigDecimal("10.0"), "USD")));
			expenseRepository.save(new Expense(category2, Long.valueOf("2"), "home", new Money(new BigDecimal("5.0"), "USD")));
			expenseRepository.save(new Expense(category3, Long.valueOf("3"), "Lee Sandwiches", new Money(new BigDecimal("15.0"), "USD")));
			expenseRepository.save(new Expense(category4, Long.valueOf("4"), "Gas 76 station", new Money(new BigDecimal("25.0"), "USD")));
            expenseRepository.save(new Expense(category5, Long.valueOf("13860428"), "test", new Money(new BigDecimal("15.00"), "USD")));
            expenseRepository.save(new Expense(category6, Long.valueOf("15117729"), "test", new Money(new BigDecimal("399.00"), "USD")));

			log.info("Categories found with findAll():");
			log.info("-------------------------------");
			for (Category category : categoryRepository.findAll()) {
				log.info(category.toString());
			}
			log.info("");


			log.info("Expenses found with findAll():");
			log.info("-------------------------------");
            int count = 0;
			for (Expense expense : expenseRepository.findAll()) {
				log.info(count + ": " + expense.getId());
                count++;
			}
			log.info("");

			// fetch an individual expense by ID
			Expense expense2 = expenseRepository.findOne(2L);
			log.info("Expense found with findOne(2L):");
			log.info("--------------------------------");
			log.info(expense2.toString());
			log.info("");

            Expense expense3 = expenseRepository.findOne(Long.valueOf("13860428"));
            log.info("Expense found with findOne(\"13860428\"):");
            log.info("--------------------------------");
            // log.info(expense3.toString());
            log.info("");

            log.info("Call Target API");
            log.info("--------------------------------");
            // Start the clock
            long start = System.currentTimeMillis();
            // RestTemplate restTemplate = new RestTemplate();
//            HttpHeaders headers = new HttpHeaders();
//            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);
//            ResponseEntity<ProductResponse> responseResponseEntity =
//                    restTemplate.exchange("https://api.target.com/products/v3/13860428?fields=descriptions&id_type=TCIN&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz",
//                            HttpMethod.GET, entity, new ParameterizedTypeReference<ProductResponse>(){});
//
//            ProductResponse response = restTemplate
//                    .getForObject("https://api.target.com/products/v3/13860428?fields=descriptions&id_type=TCIN&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz", ProductResponse.class);
//            ProductResponse response = responseResponseEntity.getBody();
            Future<ProductResponse> responseFuture = productService.findProduct("13860428");

            // Wait until all done
            while (!responseFuture.isDone()) {
                Thread.sleep(10);
            }
            log.info("Elapsed time: " + (System.currentTimeMillis() - start));
            // Quote quote = restTemplate.getForObject("http://gturnquist-quoters.cfapps.io/api/random", Quote.class);
            ProductResponse response = responseFuture.get();
            log.info(response.toString());

		};
	}


	public static void main(String[] args) {
		SpringApplication.run(ExpendituresApplication.class, args);
	}
}
