package com.ergun.auth.customer.web;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private int code;
    private String message;
    private final int id;
    private final String name;
    private final String surname;
    private final String email;
    private final int phoneNumber;
    private final String address;
    private final String password;
}
