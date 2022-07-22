
package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.MovieBasicDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.entity.MovieEntity;
import com.alkemy.disney.exception.ErrorEnum;
import com.alkemy.disney.exception.ParamNotFoundException;
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

        GenreEntity genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_GENRE_NOT_VALID.getMessage()));

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

    public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> entityList, boolean loadCharacters) {

        List<MovieDTO> dtos = new ArrayList<>();

        for (MovieEntity entity : entityList) {
            dtos.add(this.movieEntity2DTO(entity, loadCharacters));
        }

        return dtos;
    }


    public void movieEntityRefreshValues(MovieEntity entity, MovieDTO dto) {

        entity.setTitle(dto.getTitle());
        entity.setImage(dto.getImage());
        entity.setRate(dto.getRate());
        entity.setCreationDate(this.string2LocalDate(dto.getCreationDate()));

        GenreEntity genre = genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_GENRE_NOT_VALID.getMessage()));

        entity.setGenre(genre);
/*
        Set<CharacterEntity> characters = this.characterMapper.characterDTOList2Entity(dto.getCharacters());
        entity.setCharacters(characters);
*/
    }
    private MovieBasicDTO movieEntity2BasicDTO(MovieEntity entity) {

        MovieBasicDTO dto = new MovieBasicDTO();

        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setTitle(entity.getTitle());

        return dto;
    }

    public List<MovieBasicDTO> movieEntityList2DTOList(List<MovieEntity> entityList) {

        List<MovieBasicDTO> dtos = new ArrayList<>();

        for (MovieEntity entity : entityList) {
            dtos.add(this.movieEntity2BasicDTO(entity));
        }

        return dtos;
    }
}