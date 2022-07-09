package com.alkemy.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterDTO {
    private Long id;
    private String image;
    private String name;
    private String history;
    private Double weight;
    private Integer age;
    private Set<MovieDTO> movies = new HashSet<>();
}
