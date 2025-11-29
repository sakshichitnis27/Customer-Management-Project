package com.chitnis.customerservice.service;

import com.chitnis.customerservice.dto.CustomerResponseDTO;
import com.chitnis.customerservice.mapper.CustomerMapper;
import com.chitnis.customerservice.model.Customer;
import com.chitnis.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public List<CustomerResponseDTO> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(CustomerMapper::toDTO).toList();
    }


}
