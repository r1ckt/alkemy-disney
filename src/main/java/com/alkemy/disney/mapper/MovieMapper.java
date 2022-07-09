
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

    @Autowired
    private CharacterMapper characterMapper;

    @Autowired
    private GenreMapper genreMapper;

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
            Set<CharacterEntity> characterEntitySet =  characterMapper.dtoSet2EntitySet(dtoSet,true);
            movieEntity.setCharacters(characterEntitySet);
        }

        return movieEntity;
    }

    public MovieDTO movieEntity2DTO(MovieEntity movieEntity, boolean b) {

        MovieDTO movieDTO = new MovieDTO();

        movieDTO.setId(movieEntity.getId());
        movieDTO.setImage(movieEntity.getImage());
        movieDTO.setTitle(movieEntity.getTitle());
        movieDTO.setCreationDate(movieEntity.getCreationDate());
        movieDTO.setRate(movieEntity.getRate());
        movieDTO.setGenreId(movieEntity.getGenreId());

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

    public List<MovieDTO> entitySet2DtoList(List<MovieEntity> entities, boolean loadCharacters) {

        List<MovieDTO> movieDTOS = new ArrayList<>();

        for (MovieEntity entity : entities){
            movieDTOS.add(movieEntity2DTO(entity,loadCharacters));
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

}