package net.kaczmarzyk.example;

import jakarta.transaction.Transactional;
import net.kaczmarzyk.example.repo.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
@Transactional
public abstract class IntegrationTestBase {

	@Autowired
	protected CustomerRepository customerRepo;

	@Autowired
	WebApplicationContext wac;

	protected MockMvc mockMvc;

	@BeforeEach
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

}