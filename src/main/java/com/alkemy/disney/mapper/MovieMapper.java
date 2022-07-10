
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
        LocalDate date = LocalDate.parse(stringDate, formatter);
        return date;
    }

    public MovieEntity movieDTO2Entity(MovieDTO dto) {
        GenreEntity genre = genreRepository.findById(dto.getGenreId()).orElseThrow();

        MovieEntity entity = new MovieEntity();
        entity.setImage(dto.getImage());
        entity.setTitle(dto.getTitle());
        entity.setCreationDate(this.string2LocalDate(dto.getCreationDate()));
        entity.setRate(dto.getRate());
        entity.setGenre(genre);

        Set<CharacterEntity> characters = this.characterMapper.characterESet2DTOSet(dto.getCharacters()));
        entity.setCharacters(characters);

        return movie;
    }


    public MovieDTO movieEntity2DTO(MovieEntity movieEntity) {

        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(movieEntity.getId());
        movieDTO.setImage(movieEntity.getImage());
        movieDTO.setTitle(movieEntity.getTitle());
        movieDTO.setCreationDate(movieEntity.getCreationDate());
        movieDTO.setRate(movieEntity.getRate());
        movieDTO.setGenreId(movieEntity.getGenreId());

        GenreDTO genre = this.genreMapper.genreEntity2DTO(movieEntity.getGenre());
        Set<CharacterDTO> characters = this.characterMapper.characterEntitySet2DTOSet(movieEntity.getCharacters());

        movieDTO.setGenre(genre);
        movieDTO.setCharacters(characters);

        return movieDTO;
    }


    public MovieBasicDTO movieEntity2BasicDTO(MovieEntity movieEntity){

        MovieBasicDTO movieBasicDTO = new MovieBasicDTO();

        movieBasicDTO.setImage(movieEntity.getImage());
        movieBasicDTO.setTitle(movieEntity.getTitle());
        movieBasicDTO.setCreationDate(movieEntity.getCreationDate());

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
            movieDTOS.add(movieEntity2DTO(entity));
        }

        return movieDTOS;
    }

    public Set<MovieEntity> dtoSet2EntitySet(Set<MovieDTO> dtoSet, boolean loadCharacters) {

        Set<MovieEntity> entitySet = new HashSet<>();

        for (MovieDTO dto : dtoSet){
            entitySet.add(this.movieDTO2Entity(dto, loadCharacters));
        }

        return entitySet;
    }

    public MovieEntity movieEntityRefreshValues(MovieEntity movieEntity,
                                             MovieDTO movieDTO) {

        movieEntity.setId(movieDTO.getId());
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setCreationDate(movieDTO.getCreationDate());
        movieEntity.setRate(movieDTO.getRate());
        movieEntity.setGenreId(movieDTO.getGenreId());


        // TODO: setGenre and setCharacters

        return movieEntity;
    }

}