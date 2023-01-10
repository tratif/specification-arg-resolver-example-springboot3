package net.kaczmarzyk.example.repo;

import net.kaczmarzyk.example.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long>, JpaSpecificationExecutor<Customer>, JpaRepository<Customer, Long> {

	Optional<Customer> findByFirstName(String firstName);
}
