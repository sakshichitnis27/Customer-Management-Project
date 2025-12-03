package com.chitnis.customerservice.service;

import com.chitnis.customerservice.dto.CustomerRequestDTO;
import com.chitnis.customerservice.dto.CustomerResponseDTO;
import com.chitnis.customerservice.exception.CustomerNotFoundException;
import com.chitnis.customerservice.exception.EmailAlreadyExistsException;
import com.chitnis.customerservice.mapper.CustomerMapper;
import com.chitnis.customerservice.model.Customer;
import com.chitnis.customerservice.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        if (customerRepository.existsByEmail(customerRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "A customer with this email " + "already exists"
                            + customerRequestDTO.getEmail());
        }
        Customer newCustomer = customerRepository.save(
                CustomerMapper.toModel(customerRequestDTO));

        return CustomerMapper.toDTO(newCustomer);

    }

    public CustomerResponseDTO updateCustomer(UUID id,
                                            CustomerRequestDTO customerRequestDTO) {

        Customer customer = customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found with ID: " + id));

        if (customerRepository.existsByEmailAndIdNot(customerRequestDTO.getEmail(),id)) {
            throw new EmailAlreadyExistsException(
                    "A customer with this email " + "already exists"
                            + customerRequestDTO.getEmail());
        }

        customer.setName(customerRequestDTO.getName());
        customer.setAddress(customerRequestDTO.getAddress());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setDateOfBirth(LocalDate.parse(customerRequestDTO.getDateOfBirth()));

        Customer updatedCustomer = customerRepository.save(customer);

        return CustomerMapper.toDTO(updatedCustomer);
    }

    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }

}
