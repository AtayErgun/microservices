package com.ergun.auth.customer.web;

import com.ergun.auth.customer.api.CustomerDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerRequest {
    private String name;
    private String surname;

    private String email;
    private int phoneNumber;
    private String address;
    private String password;

    public CustomerDto toDto(){
        return  CustomerDto.builder()
                .name(this.name)
                .surname(this.surname)
                .email(this.email)
                .phoneNumber(this.phoneNumber)
                .address(this.address)
                .password(this.password)
                .build();
    }
}
