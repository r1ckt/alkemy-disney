package com.alkemy.disney.dto;

import com.alkemy.disney.entity.MovieEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreDTO {
    private Long id;
    private String name;
    private String image;
}
