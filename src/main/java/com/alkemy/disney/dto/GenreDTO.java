package com.alkemy.disney.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
public class GenreDTO {
    private Long id;
    @NotNull
    private String name;
    private String image;
}
