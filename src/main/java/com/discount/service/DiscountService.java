package com.discount.service;

import com.discount.domain.Customer;

import java.math.BigDecimal;

/**
 * The interface Discount service.
 */
public interface DiscountService {

    /**
     * Calculate discount big decimal.
     *
     * @param customer the customer
     * @return the big decimal
     */
    BigDecimal calculateDiscount(Customer customer, Long invoiceId);
}
