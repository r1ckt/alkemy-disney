package com.alkemy.disney.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthenticationRequest {
    @Email(message = "You must enter an email")
    private String username;
    @NotBlank(message = "You must enter a password")
    private String password;
}
