package com.discount.service;

import com.discount.dtos.CustomerDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * The interface Customer service.
 */
public interface CustomerService {

    /**
     * Gets all customers.
     *
     * @return the all customers
     */
    List<CustomerDTO> getAllCustomers();

    /**
     * Find customer by id customer dto.
     *
     * @param id the id
     * @return the customer dto
     */
    CustomerDTO findCustomerById(Long id);

    /**
     * Gets discountd amount.
     *
     * @param customerId the customer id
     * @param invoiceId  the invoice id
     * @return the discountd amount
     */
    BigDecimal getDiscountAmount(Long customerId, Long invoiceId);
}
