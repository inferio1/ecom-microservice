package com.ecommerce.user.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private AddressDTO address;

}

