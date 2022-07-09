package com.alkemy.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CharacterBasicDTO {
    private Long id;
    private String image;
    private String name;
}
