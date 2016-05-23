package net.hereisjohnny;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;



import net.hereisjohnny.dao.CategoryRepository;
import net.hereisjohnny.dao.ExpenseRepository;
import net.hereisjohnny.webservice.model.Category;
import net.hereisjohnny.webservice.model.Expense;
import net.hereisjohnny.webservice.model.Money;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by gomez on 5/17/16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ExpendituresApplication.class)
@WebAppConfiguration
public class ExpenseControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String categoryName = "breakfast";

    private Category category;

    private List<Expense> expenseList = new ArrayList<>();

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
          hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("The JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setUp() throws Exception {
        Locale locale = Locale.US;
        Currency currency = Currency.getInstance(locale);
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.expenseRepository.deleteAllInBatch();
        this.categoryRepository.deleteAllInBatch();
        BigDecimal ten_dollers = new BigDecimal("10.0");
        BigDecimal five_dollers = new BigDecimal("5.0");

        this.category = categoryRepository.save(new Category("test"));
        this.expenseList.add(expenseRepository.save(new Expense(category, "test1", new Money(ten_dollers, "USD"))));
        this.expenseList.add(expenseRepository.save(new Expense(category, "test2", new Money(five_dollers, "USD"))));

    }

    @Test
    public void categoryNotFound() throws Exception {
        mockMvc.perform(get("/unknown/expenses"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleExpense() throws Exception {
        Long id = this.expenseList.get(0).getId();

        mockMvc.perform(get("/test/expenses/" + this.expenseList.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(id.intValue())))
                .andExpect(jsonPath("$.current_price.value", is(10.0)))
                .andExpect(jsonPath("$.current_price.currency_code", is("USD")))
                .andExpect(jsonPath("$.name", is("test1")));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}
