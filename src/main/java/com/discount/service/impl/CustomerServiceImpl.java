package com.discount.service.impl;

import static java.lang.String.format;

import com.discount.domain.Customer;
import com.discount.dtos.CustomerDTO;
import com.discount.repository.CustomerRepository;
import com.discount.service.CustomerService;
import com.discount.service.DiscountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Customer service.
 */
@Log4j2
@Service
public class CustomerServiceImpl implements CustomerService {

    private static final String CUSTOMER_DOES_NOT_EXIST = "Customer with id %s does not exist.";


    private DiscountService discountService;
    private CustomerRepository customerRepository;

    /**
     * Instantiates a new Customer service.
     *
     * @param discountService    the discount service
     * @param customerRepository the customer repository
     */
    public CustomerServiceImpl(DiscountService discountService, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public List<CustomerDTO> getAllCustomers() {

        log.info("Getting all Customers");

        return this.customerRepository .findAll()
                .stream()
                .map(customer -> CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .customerType(customer.getCustomerType())
                .dateCreated(customer.getDateCreated()).build())
                .collect(Collectors.toList());
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public CustomerDTO findCustomerById(Long id) {

        log.info("Find customer by id {}", id);

        Customer customer = this.findById(id);
        return  CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .customerType(customer.getCustomerType())
                .dateCreated(customer.getDateCreated()).build();
    }


    /**
     * @{inheritDoc}
     */
    @Override
    public BigDecimal getDiscountAmount(Long customerId, Long invoiceId){

        log.info("Getting discount for customer {} and invoice {}", customerId,  invoiceId);
        Customer customer = this.findById(customerId);
        log.trace("Customer Details {}", customer);

        BigDecimal discountAmount = this.discountService.calculateDiscount(customer,invoiceId);
        log.info("Customer with Id {} have a discount amount of {}", customerId, discountAmount);

        return discountAmount;

    }


    /**
     * Find customer by id customer.
     *
     * @param id the id
     * @return the customer
     */
    private Customer findById (Long id){
        return  this.customerRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(format(CUSTOMER_DOES_NOT_EXIST, id)));
    }

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }
}
