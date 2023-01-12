package com.tratif.example.web;

import org.springframework.data.jpa.domain.Specification;

import com.tratif.example.domain.Customer;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@Spec(path = "deleted", constVal = "false", spec = Equal.class)
public interface NotDeletedCustomerSpecification extends Specification<Customer> {
}
