package com.alkemy.disney.auth.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

    @Email(message = "You must enter an email")
    private String username;
    @Size(min = 8, max = 16, message = "Password must be eight or sixteen characters long")
    private String password;


}
