package com.alkemy.disney.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
public class CharacterDTO {
    private Long id;

    private String image;

    @NotNull
    private String name;

    @Lob
    private String history;

    @Min(1)
    private Double weight;

    @Min(1) @Max(100)
    private Integer age;

    private List<MovieDTO> movies;
}
