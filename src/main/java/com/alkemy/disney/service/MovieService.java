package com.alkemy.disney.service;

import com.alkemy.disney.dto.MovieBasicDTO;
import com.alkemy.disney.dto.MovieDTO;

import java.util.List;

public interface MovieService {
    MovieDTO create(MovieDTO dto);
    void delete(Long id);
    MovieDTO update(Long id, MovieDTO dto);
    void addCharacter(Long idMovie, Long idCharact);
    void removeCharacter(Long idMovie, Long idCharact);

    List<MovieBasicDTO> getMovieByFilters(String title, String creationDate, Long genreId, String order);

}
