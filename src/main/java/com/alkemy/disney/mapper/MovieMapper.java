
package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.entity.MovieEntity;
import com.alkemy.disney.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class MovieMapper {

    private CharacterMapper characterMapper;
    private GenreMapper genreMapper;
    private GenreRepository genreRepository;

    @Autowired
    public MovieMapper(@Lazy CharacterMapper characterMapper,
                             GenreMapper genreMapper,
                             GenreRepository genreRepository) {

        this.genreRepository = genreRepository;
        this.characterMapper = characterMapper;
        this.genreMapper = genreMapper;
    }

    private LocalDate string2LocalDate(String stringDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(stringDate, formatter);
    }

    public MovieEntity movieDTO2Entity(MovieDTO dto) {

        GenreEntity genre = genreRepository.findById(dto.getGenreId()).orElseThrow();

        MovieEntity movie = new MovieEntity();

        movie.setGenre(genre);
        movie.setRate(dto.getRate());
        movie.setImage(dto.getImage());
        movie.setTitle(dto.getTitle());
        movie.setCreationDate(this.string2LocalDate(dto.getCreationDate()));

        Set<CharacterEntity> characters = this.characterMapper.characterDTOList2Entity(dto.getCharacters());

        movie.setCharacters(characters);
        return movie;
    }

    public MovieDTO movieEntity2DTO(MovieEntity entity, boolean loadCharacters) {

        MovieDTO dto = new MovieDTO();

        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setTitle(entity.getTitle());
        dto.setCreationDate(entity.getCreationDate().toString());
        dto.setRate(entity.getRate());
        dto.setGenreId(entity.getGenre().getId());


        if (loadCharacters) {
            List<CharacterDTO> dtos = this.characterMapper.characterEntitySet2DTOList(entity.getCharacters(), false);
            dto.setCharacters(dtos);
        }

        return dto;
    }

    public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> movies, boolean loadCharacters) {

        List<MovieDTO> moviesDTOS = new ArrayList<>();

        for (MovieEntity entity : movies) {
            moviesDTOS.add(this.movieEntity2DTO(entity, loadCharacters));
        }

        return moviesDTOS;
    }


    public MovieEntity movieEntityRefreshValues(MovieEntity entity, MovieDTO movieDTO) {

        entity.setTitle(movieDTO.getTitle());
        entity.setImage(movieDTO.getImage());
        entity.setRate(movieDTO.getRate());
        entity.setCreationDate(this.string2LocalDate(movieDTO.getCreationDate()));

        GenreEntity genre = genreRepository.findById(movieDTO.getGenreId()).get();
        entity.setGenre(genre);

        Set<CharacterEntity> characters = this.characterMapper.characterDTOList2Entity(movieDTO.getCharacters());
        entity.setCharacters(characters);

        return entity;
    }
}