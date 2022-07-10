
package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.MovieBasicDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MovieMapper {

    private CharacterMapper movieMapper;
    private GenreMapper genreMapper;


    @Autowired
    public MovieMapper(@Lazy CharacterMapper movieMapper,
                       GenreMapper genreMapper) {

        this.movieMapper = movieMapper;
        this.genreMapper = genreMapper;
    }

    public MovieEntity movieDTO2Entity(MovieDTO movieDTO, boolean loadCharacters) {

        MovieEntity movieEntity = new MovieEntity();

        if(movieDTO.getId()!=null){
            movieEntity.setId(movieDTO.getId());
        }

        movieEntity.setImage(movieDTO.getImage());
        movieEntity.setTitle(movieDTO.getTitle());
        movieEntity.setCreationDate(movieDTO.getCreationDate());
        movieEntity.setRate(movieDTO.getRate());
        movieEntity.setGenreId(movieDTO.getGenreId());

        if(loadCharacters){
            Set<CharacterDTO> dtoSet = movieDTO.getCharacters();
            Set<CharacterEntity> movieEntitySet =  movieMapper.dtoSet2EntitySet(dtoSet,true);
            movieEntity.setCharacters(movieEntitySet);
        }

        return movieEntity;
    }

    public MovieDTO movieEntity2DTO(MovieEntity movieEntity) {

        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(movieEntity.getId());
        movieDTO.setImage(movieEntity.getImage());
        movieDTO.setTitle(movieEntity.getTitle());
        movieDTO.setCreationDate(movieEntity.getCreationDate());
        movieDTO.setRate(movieEntity.getRate());
        movieDTO.setGenreId(movieEntity.getGenreId());
        movieDTO.setGenre(movieDTO.getGenre());

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
            entitySet.add(this.movieDTO2Entity(dto,loadCharacters));
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