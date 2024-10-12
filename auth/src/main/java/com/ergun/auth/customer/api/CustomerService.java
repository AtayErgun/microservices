package com.ergun.auth.customer.api;



import java.util.List;

public interface CustomerService {
    CustomerDto save(CustomerDto customerDto);
    CustomerDto update(CustomerDto customerDto , String id);
    CustomerDto getCustomer(String id);
    List<CustomerDto> getAllCustomer();
    void delete(String id);
}
