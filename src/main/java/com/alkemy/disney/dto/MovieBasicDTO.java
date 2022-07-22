package com.alkemy.disney.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieBasicDTO {

    private String image;
    private String title;
    private String creationDate;
}
