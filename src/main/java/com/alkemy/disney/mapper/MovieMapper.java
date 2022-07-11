
package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.dto.MovieBasicDTO;
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
import java.util.HashSet;
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
                                            .orElseThrow();

        MovieEntity movie = new MovieEntity();

        movie.setGenre(genre);
        movie.setRate(dto.getRate());
        movie.setImage(dto.getImage());
        movie.setTitle(dto.getTitle());
        movie.setCreationDate(this.string2LocalDate(dto.getCreationDate()));

        Set<CharacterEntity> characters =
                this.characterMapper.characterDTOList2Entity(dto.getCharacters());

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
            List<CharacterDTO> characterDTOS =
                    this.characterMapper
                    .characterEntitySet2DTOList(entity.getCharacters(), false);

            dto.setCharacters(characterDTOS);
        }

        return dto;
    }


    public MovieBasicDTO movieEntity2BasicDTO(MovieEntity movieEntity){

        MovieBasicDTO movieBasicDTO = new MovieBasicDTO();

        movieBasicDTO.setImage(movieEntity.getImage());
        movieBasicDTO.setTitle(movieEntity.getTitle());
        movieBasicDTO.setCreationDate(movieEntity.getCreationDate().toString());

        return movieBasicDTO;
    }

    public List<MovieBasicDTO> movieEntitySet2BasicDTOSet(List<MovieEntity> movieEntities){

        List<MovieBasicDTO> dtos = new ArrayList<>();

        for (MovieEntity entity : movieEntities){
            dtos.add(movieEntity2BasicDTO(entity));
        }

        return dtos;
    }

    public List<MovieDTO> entitySet2DtoList(List<MovieEntity> entities) {

        List<MovieDTO> movieDTOS = new ArrayList<>();

        for (MovieEntity entity : entities){
            movieDTOS.add(movieEntity2DTO(entity, false));
        }

        return movieDTOS;
    }

    public Set<MovieEntity> dtoSet2EntitySet(Set<MovieDTO> dtoSet,
                                             boolean loadCharacters) {

        Set<MovieEntity> entitySet = new HashSet<>();

        for (MovieDTO dto : dtoSet){
            entitySet.add(this.movieDTO2Entity(dto));
        }

        return entitySet;
    }

    public MovieEntity movieEntityRefreshValues(MovieEntity movieEntity,
                                             MovieDTO movieDTO) {

        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setCreationDate(this.string2LocalDate(movieDTO.getCreationDate()));
        movieEntity.setRate(movieDTO.getRate());

        GenreEntity genre = genreRepository.getById(movieDTO.getGenreId());

        movieEntity.setGenre(genre);

        Set<CharacterEntity> characters = this.characterMapper.
                                characterDTOList2Entity(movieDTO.getCharacters());

        return movieEntity;
    }

    public List<MovieDTO> movieEntityList2DTOList(List<MovieEntity> movies,
                                                  boolean loadCharacters) {

        List<MovieDTO> moviesDTOS = new ArrayList<>();

        for (MovieEntity movie:movies) {
            moviesDTOS.add(this.movieEntity2DTO(movie, loadCharacters));
        }

        return moviesDTOS;
    }

}