package com.chitnis.customerservice.mapper;

import com.chitnis.customerservice.dto.CustomerResponseDTO;
import com.chitnis.customerservice.model.Customer;

public class CustomerMapper {

    public static CustomerResponseDTO toDTO(Customer customer) {
        CustomerResponseDTO customerDTO = new CustomerResponseDTO();
        customerDTO.setId(customer.getId().toString());
        customerDTO.setName(customer.getName());
        customerDTO.setAddress(customer.getAddress());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setDateOfBirth(customer.getDateOfBirth().toString());

        return customerDTO;
    }
}
