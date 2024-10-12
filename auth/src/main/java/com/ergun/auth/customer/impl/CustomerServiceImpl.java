package com.ergun.auth.customer.impl;


import com.ergun.auth.customer.api.CustomerDto;
import com.ergun.auth.customer.api.CustomerService;
import com.ergun.auth.customer.exception.custom_errors.EmailAlreadyExistsException;
import com.ergun.auth.customer.exception.custom_errors.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;



//    public CustomerServiceImpl(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }
//    Yukarıdaki constuctor'ı silmemin sebebi = RequiredArgsConstructor anotasyonu

    @Override
    public CustomerDto save(CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = Optional.ofNullable(customerRepository.findCustomerByEmail(customerDto.getEmail()));
        if(optionalCustomer.isPresent()){
            throw new EmailAlreadyExistsException("Email Already Exists For Another Customer");
        }
        Customer customer = toEntity(customerDto);
        customer = customerRepository.save(customer);
        return toDto(customer);
    }

    @Override
    public CustomerDto update(CustomerDto customerDto, String id) {
        Customer customer = customerRepository.findById(Integer.parseInt(id)).orElseThrow(
                ()-> new ResourceNotFoundException("Customer" , "id" , id)
        );
        customer.setName(customerDto.getName());
        customer.setSurname(customerDto.getSurname());
        customer.setEmail(customerDto.getEmail());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customer.setPassword(customerDto.getPassword());
        customer = customerRepository.save(customer);
        return toDto(customer);
    }

    @Override
    public CustomerDto getCustomer(String id) {
        Customer customer = customerRepository.findById(Integer.parseInt(id)).orElseThrow(
                () -> new ResourceNotFoundException("Customer" , "id" , id)
        );
        return toDto(customer);
    }

    public Customer findCustomerEntity(String id){
        return customerRepository.findById(Integer.parseInt(id)).get();
    }


    @Override
    public List<CustomerDto> getAllCustomer() {
        return null;
    }

    @Override
    public void delete(String id) {
        Customer customer = customerRepository.findById(Integer.parseInt(id)).orElseThrow(
                ()-> new ResourceNotFoundException("Customer" , "id" , id)
        );
        customerRepository.delete(customer);
    }



    // Repository'e gitmeden önce çalışacak metod
    public Customer toEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setSurname(customerDto.getSurname());
        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setAddress(customerDto.getAddress());
        customer.setPassword(customerDto.getPassword());
        return customer;
    }




    // Response'a gitmeden önce çalışacak olan toDto
    public CustomerDto toDto(Customer customer) {
        return CustomerDto.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .password(customer.getPassword())
                .address(customer.getAddress())
                .build();
    }
}
