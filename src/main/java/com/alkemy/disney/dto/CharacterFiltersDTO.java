package com.alkemy.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CharacterFiltersDTO {
    private String name;
    private Long age;
    private Long weight ;
    private List<Long> movies;

    public CharacterFiltersDTO(String name, Long age, Long weight, List<Long> movies) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.movies = movies;
    }
}
