package com.discount.support;

import com.discount.domain.Customer;
import com.discount.domain.Invoice;
import com.discount.domain.Item;
import com.discount.domain.enums.CustomerType;
import com.discount.domain.enums.ItemType;
import com.discount.repository.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class DataLoader implements CommandLineRunner {

    private CustomerRepository customerRepository;

    public DataLoader(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("Loading data...");
        List<Customer> customers = new ArrayList<>();

        Customer customer = Customer.builder()
                .firstName("Jean")
                .lastName("Sadde")
                .customerType(CustomerType.AFFILIATE)
                .build();
        customer.setInvoices(getInvoices(customer));
        customers.add(customer);


        customer = Customer.builder()
                .firstName("Hussein")
                .lastName("Sabra")
                .customerType(CustomerType.EMPLOYEE)
                .build();
        customer.setInvoices(getInvoices(customer));
        customers.add(customer);

        this.customerRepository.saveAll(customers);
        log.info("Data Loaded...");
    }

    private List<Invoice> getInvoices(Customer customer) {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice = Invoice.builder()
                .customer(customer)
                .name("Fahed Supermarket").build();
        invoices.add(invoice);

        List<Item> items = new ArrayList<>();
        items.add(Item.builder()
                .invoice(invoice)
                .name("Grocery1")
                .itemType(ItemType.GROCERY)
                .invoice(invoice)
                .itemPrice(BigDecimal.valueOf(150)).build());

        items.add(Item.builder()
                .invoice(invoice)
                .name("Steak")
                .itemType(ItemType.MEAT)
                .invoice(invoice)
                .itemPrice(BigDecimal.valueOf(400)).build());


        items.add(Item.builder()
                .invoice(invoice)
                .name("Raw Meat")
                .itemType(ItemType.OTHER)
                .invoice(invoice)
                .itemPrice(BigDecimal.valueOf(350)).build());

        invoice.setItems(items);

        return invoices;
    }
}
