package com.discount.controller;

import com.discount.dtos.CustomerDTO;
import com.discount.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
@RestController
@RequestMapping(value = "customers")
public class CustomerController {


    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Rest Resource that get the list of All store customers
     *
     * @return list of all {}
     */
    @ApiOperation(
            notes = "Returns  list of all store customers.",
            value = "Get a list of all store customers.",
            nickname = "listAll",
            tags = {"customers"})
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        return new ResponseEntity<>(this.customerService.getAllCustomers(),HttpStatus.OK);
    }

    /**
     * Rest Resource that return single Customer by Id
     *
     * @param customerId
     * @return customer
     */
    @ApiOperation(notes = "Returns a Customer.",
            value = "Get Customer",
            nickname = "getById",
            tags = {"customers"})
    @GetMapping(value = "/{customer-id}")
    public ResponseEntity<CustomerDTO> getAllCustomerById(@PathVariable("customer-id") Long customerId) {

        CustomerDTO customerDTO = this.customerService.findCustomerById(customerId);
        log.info("GET Customer with id {}: {}", customerId, customerDTO);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

    /**
     * Rest Resource that return single Customer by Id
     *
     * @param customerId
     * @return customer
     */
    @ApiOperation(notes = "Returns a Customer.",
            value = "Get Customer",
            nickname = "getById",
            tags = {"customers"})
    @GetMapping(value = "{customer-id}/invoice/{invoice-id}")
    public ResponseEntity<BigDecimal> getAmountDetail(@PathVariable("customer-id") Long customerId,
                                                      @PathVariable("invoice-id") Long invoiceId) {
        BigDecimal discount = this.customerService.getDiscountAmount(customerId, invoiceId);
        log.info("Customer Discount amount {}", discount);
        return new ResponseEntity<>(discount, HttpStatus.OK);
    }
}
