package com.alkemy.disney.dto;

import com.sun.istack.NotNull;
import lombok.Getter;

import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Setter
public class MovieDTO {
    private Long id;

    private String image;

    @NotNull
    private String title;

    private String creationDate;

    @Min(1) @Max(5)
    private Integer rate;

    private List<CharacterDTO> characters;

    @NotNull
    private Long genreId;
}
