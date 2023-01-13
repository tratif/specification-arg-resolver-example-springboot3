package com.tratif.example.web;

import com.tratif.example.IntegrationTestBase;
import com.tratif.example.domain.Customer;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends IntegrationTestBase {

	@Test
	public void returnsAllCustomers() throws Exception {
		mockMvc.perform(get("/customers"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].firstName").value("Homer"))
				.andExpect(jsonPath("$[1].firstName").value("Marge"))
				.andExpect(jsonPath("$[2].firstName").value("Bart"))
				.andExpect(jsonPath("$[3].firstName").value("Lisa"))
				.andExpect(jsonPath("$[4].firstName").value("Barney"))
				.andExpect(jsonPath("$[5].firstName").value("Moe"))
				.andExpect(jsonPath("$[6]").doesNotExist());
	}

	@Test
	public void softDeletesCustomer() throws Exception {
		mockMvc.perform(delete("/customers/1"))
				.andExpect(status().isOk());

		Optional<Customer> softDeletedCustomer = customerRepo.findById(1L);

		assertThat(softDeletedCustomer)
				.isPresent();
		assertThat(softDeletedCustomer.get().isDeleted())
				.isTrue();
	}

	@Test
	public void returnsNotDeletedCustomersByFirstName() throws Exception {
		Optional<Customer> customerToDelete = customerRepo.findByFirstName("Bart");

		assertThat(customerToDelete).isPresent();

		customerToDelete.get().delete();

		mockMvc.perform(get("/customers")
				.param("firstName", "Ba"))
				.andExpect(jsonPath("$[0].firstName").value("Barney"));
	}

	@Test
	public void returnsCustomersByLastNameAndGender() throws Exception {
		mockMvc.perform(get("/customers")
				.param("lastName", "Simpson")
				.param("gender", "F"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].firstName").value("Marge"))
				.andExpect(jsonPath("$[1].firstName").value("Lisa"))
				.andExpect(jsonPath("$[2]").doesNotExist());
	}

	@Test
	public void returnsCustomersByNamePaged() throws Exception {
		mockMvc.perform(get("/customers")
				.param("firstName", "Homer")
				.param("page", "1"))
				.andExpect(jsonPath("$[0].firstName").value("Homer"))
				.andExpect(jsonPath("$[1].firstName").doesNotExist());
	}

	@Test
	public void returnsCustomersRegisteredBefore() throws Exception {
		mockMvc.perform(get("/customers")
				.param("registeredBefore", "2014-01-14"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].firstName").value("Homer"))
				.andExpect(jsonPath("$[1].firstName").value("Marge"))
				.andExpect(jsonPath("$[2]").doesNotExist());
	}

	@Test
	public void returnsCustomersByRegistrationDate() throws Exception {
		mockMvc.perform(get("/customers")
				.param("registeredAfter", "2014-01-01")
				.param("registeredBefore", "2014-01-21"))
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$[0].firstName").value("Homer"))
				.andExpect(jsonPath("$[1].firstName").value("Marge"))
				.andExpect(jsonPath("$[2].firstName").value("Bart"))
				.andExpect(jsonPath("$[3]").doesNotExist());
	}

	@Test
	public void returnsCustomersByGenderAndName() throws Exception {
		Optional<Customer> customerToDelete = customerRepo.findByFirstName("Homer");

		assertThat(customerToDelete).isPresent();

		customerToDelete.get().delete();

		mockMvc.perform(get("/customers")
				.param("name", "Simpson")
				.param("gender", "M"))
				.andExpect(jsonPath("$[0].firstName").value("Bart"))
				.andExpect(jsonPath("$[1]").doesNotExist());
	}

	@Test
	public void returnsNotDeletedCustomersByRegistrationDateAndName() throws Exception {
		Optional<Customer> customerToDelete1 = customerRepo.findByFirstName("Homer");
		Optional<Customer> customerToDelete2 = customerRepo.findByFirstName("Bart");

		assertThat(customerToDelete1).isPresent();
		assertThat(customerToDelete2).isPresent();

		customerToDelete1.get().delete();
		customerToDelete2.get().delete();

		mockMvc.perform(get("/customers")
				.param("registeredBefore", "2014-01-22")
				.param("name", "Simpson"))
				.andExpect(jsonPath("$[0].firstName").value("Marge"))
				.andExpect(jsonPath("$[1].firstName").value("Lisa"));
	}

	private Date date(int year, int month, int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}
}