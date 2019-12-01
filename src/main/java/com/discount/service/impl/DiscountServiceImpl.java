package com.discount.service.impl;

import com.discount.domain.Customer;
import com.discount.domain.Invoice;
import com.discount.domain.Item;
import com.discount.domain.enums.ItemType;
import com.discount.service.DiscountService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The type Discount service.
 */
@Log4j2
@Service
public class DiscountServiceImpl implements DiscountService {


    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100);
    private static final BigDecimal EMPLOYEE_DISCOUNT = BigDecimal.valueOf(30);
    private static final BigDecimal AFFILIATE_DISCOUNT = BigDecimal.valueOf(10);
    private static final BigDecimal CUSTOMER_OVER_TWO_YEARS = BigDecimal.valueOf(5);
    private static final BigDecimal SPENDING_OVER_100 = BigDecimal.valueOf(5);

    /**
     * Calculate discount big decimal.
     *
     * @param customer  the customer
     * @param invoiceId the invoice id
     * @return the big decimal
     * @{inheritDoc}
     */
    @Override
    public BigDecimal calculateDiscount(Customer customer, Long invoiceId) {


        Invoice invoice = getInvoice(customer, invoiceId);

        final BigDecimal totalPurchaseAmount = calculateDiscountItems(invoice.getItems());
        if (totalPurchaseAmount.equals(BigDecimal.ZERO)) {
            log.info("Total discount  items is 0");
            return totalPurchaseAmount;
        }

        switch (customer.getCustomerType()) {
            case EMPLOYEE:
                return calculatePercentage(EMPLOYEE_DISCOUNT, totalPurchaseAmount);
            case AFFILIATE:
                return calculatePercentage(AFFILIATE_DISCOUNT, totalPurchaseAmount);
            default:
                return calculateCustomerDiscounts(customer, totalPurchaseAmount);
        }
    }

    /**
     * Calculate customer discounts big decimal.
     *
     * @param customer            the customer
     * @param totalPurchaseAmount the total purchase amount
     * @return the big decimal
     */
    private BigDecimal calculateCustomerDiscounts(Customer customer, BigDecimal totalPurchaseAmount) {
        if (customer.getDateCreated().isBefore(LocalDateTime.now().minusYears(2))) {
            return calculatePercentage(CUSTOMER_OVER_TWO_YEARS, totalPurchaseAmount);
        }
        if (totalPurchaseAmount.compareTo(HUNDRED) > 0) {
            return totalPurchaseAmount.divide(HUNDRED, 0, RoundingMode.DOWN).multiply(SPENDING_OVER_100);
        }
        return BigDecimal.ZERO;
    }

    /**
     * Calculate discount items big decimal.
     *
     * @param purchasedItems the purchased items
     * @return the big decimal
     */
    private BigDecimal calculateDiscountItems(List<Item> purchasedItems) {
        return purchasedItems.stream()
                .filter(i -> i.getItemType() != ItemType.GROCERY)
                .map(Item::getItemPrice)
                .reduce(BigDecimal::add).orElse(BigDecimal.valueOf(0));
    }

    /**
     * Calculate percentage big decimal.
     *
     * @param discount the discount
     * @param total    the total
     * @return the big decimal
     */
    private BigDecimal calculatePercentage(BigDecimal discount, BigDecimal total) {
        return total.multiply(discount).divide(HUNDRED, 1, RoundingMode.FLOOR);
    }
    /**
     * Gets invoice.
     *
     * @param customer  the customer
     * @param invoiceId the invoice id
     * @return the invoice
     */
    private Invoice getInvoice(Customer customer, Long invoiceId) {

        return customer.getInvoices()
                .stream()
                .filter(invoice -> invoice.getId().equals(invoiceId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Invoice Number ["+invoiceId+"] Not Found"));
    }
}
