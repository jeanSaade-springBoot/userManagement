package com.discount.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.discount.domain.Customer;
import com.discount.domain.enums.CustomerType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void whenSaveCustomerIsOk() {


        Customer customer = Customer.builder()
                .firstName("Jean")
                .lastName("Sadde")
                .customerType(CustomerType.AFFILIATE)
                .build();


        customer = customerRepository.save(customer);
        Optional<Customer> customerOptional = customerRepository.findById(customer.getId());

        assertNotNull(customerOptional.isPresent());
        assertEquals(customer.getFirstName(),customerOptional.get().getFirstName());
        assertEquals(customer.getLastName(),customerOptional.get().getLastName());
        assertNotNull(customerOptional.get().getDateCreated());
    }
}
