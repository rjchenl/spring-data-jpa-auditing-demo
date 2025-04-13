package com.example.service;

import com.example.dto.CustomerDTO;
import com.example.entity.Customer;
import com.example.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setJobTitle(customerDTO.getJobTitle());
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer updateCustomer(Long id, CustomerDTO customerDTO) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
        
        customer.setCustomerName(customerDTO.getCustomerName());
        customer.setJobTitle(customerDTO.getJobTitle());
        return customerRepository.save(customer);
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id: " + id));
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new EntityNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }
} 