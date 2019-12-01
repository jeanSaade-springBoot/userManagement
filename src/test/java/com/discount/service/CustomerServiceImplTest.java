package com.discount.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import com.discount.domain.Customer;
import com.discount.domain.Invoice;
import com.discount.domain.Item;
import com.discount.domain.enums.CustomerType;
import com.discount.domain.enums.ItemType;
import com.discount.dtos.CustomerDTO;
import com.discount.repository.CustomerRepository;
import com.discount.service.impl.CustomerServiceImpl;
import com.discount.service.impl.DiscountServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {


    @Mock
    private CustomerRepository customerRepository;

    private DiscountService discountService;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Long id;
    private Customer customer;


    @Before
    public void setup() {

        this.discountService = new DiscountServiceImpl();
        this.customerService.setDiscountService(this.discountService);

        id = 1L;

         customer = Customer.builder()
                 .id(1L)
                .firstName("Jean")
                .lastName("Sadde")
                .customerType(CustomerType.AFFILIATE)
                .build();

        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice = Invoice.builder()
                 .id(2L)
                 .name("Fahed Supermarket").build();
        invoices.add(invoice);

        List<Item> items = new ArrayList<>();
        items.add(Item.builder()
                .name("Grocery1")
                .itemType(ItemType.GROCERY)
                .itemPrice(BigDecimal.valueOf(150)).build());

        items.add(Item.builder()
                .name("Steak")
                .itemType(ItemType.MEAT)
                .itemPrice(BigDecimal.valueOf(400)).build());


        items.add(Item.builder()
                .name("Raw Meat")
                .itemType(ItemType.MEAT)
                .itemPrice(BigDecimal.valueOf(350)).build());

        invoice.setItems(items);
        customer.setInvoices(invoices);

    }

    @After
    public void tearDown() {

        this.expectedException = ExpectedException.none();
    }

    @Test
    public void whenCustomerFindById_NotFound_throwException() {

        this.expectedException.expect(EntityNotFoundException.class);
        this.expectedException.expectMessage("Customer with id "+id+" does not exist");

        when(this.customerRepository.findById(id)).thenReturn(Optional.empty());

        customerService.findCustomerById(id);
    }

    @Test
    public void whenCustomerFindById_isOk(){

        when(this.customerRepository.findById(id)).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerService.findCustomerById(id);

        assertEquals(customerDTO.getFirstName(),customer.getFirstName());
        assertEquals(customerDTO.getLastName(),customer.getLastName());
    }

    @Test
    public void whenFindAllCustomers_isOk(){


        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(this.customerRepository.findAll()).thenReturn(customers);

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertEquals(1,result.size());
        assertEquals(customer.getLastName(),result.get(0).getLastName());
    }


    @Test
    public void whenTestDiscount_isOk(){

        when(this.customerRepository.findById(id)).thenReturn(Optional.of(customer));

        BigDecimal discount = this.customerService.getDiscountAmount(customer.getId(), 2L);
        assertEquals(BigDecimal.valueOf(75.0),discount);

    }



}
