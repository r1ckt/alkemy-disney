package com.alkemy.disney.dto;

import com.alkemy.disney.exception.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ApiErrorDTO {
    private HttpStatus status;
    private String message;
    private List<String> errors;
}
