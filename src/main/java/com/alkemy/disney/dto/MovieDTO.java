package com.alkemy.disney.dto;

import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.entity.GenreEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
    private Long id;
    private String image;
    private String title;
    private LocalDate creationDate;
    private Integer rate;
    private Set<CharacterDTO> characters = new HashSet<>();
    private GenreDTO genre;
    private Long genreId;
}
