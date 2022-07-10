package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.entity.MovieEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharacterMapper {

    @Autowired
    private MovieMapper movieMapper;

    public CharacterEntity characterDTO2Entity(CharacterDTO characterDTO, boolean loadMovies) {

        CharacterEntity characterEntity = new CharacterEntity();

        characterEntity.setImage(characterDTO.getImage());
        characterEntity.setName(characterDTO.getName());
        characterEntity.setAge(characterDTO.getAge());
        characterEntity.setWeight(characterDTO.getWeight());
        characterEntity.setHistory(characterDTO.getHistory());

        Set<MovieEntity> movies = this.movieMapper.dtoSet2EntitySet(characterDTO.getMovies(), true);
        characterEntity.setMovies(movies);

        if(loadMovies){
            Set<MovieDTO> dtoSet = characterDTO.getMovies();
            Set<MovieEntity> movieEntities = movieMapper.dtoSet2EntitySet(dtoSet, true);
            characterEntity.setMovies(movieEntities);
        }

        return characterEntity;
    }



    public CharacterDTO characterEntity2DTO(CharacterEntity characterEntity, boolean loadMovies){

        CharacterDTO characterDTO = new CharacterDTO();

        characterDTO.setId(characterEntity.getId());
        characterDTO.setImage(characterEntity.getImage());
        characterDTO.setName(characterEntity.getName());
        characterDTO.setAge(characterEntity.getAge());
        characterDTO.setWeight(characterEntity.getWeight());
        characterDTO.setHistory(characterEntity.getHistory());

        return characterDTO;
    }

    public List<CharacterBasicDTO> CharacterEntitySet2BasicDTOSet (List<CharacterEntity> characterEntities){

        List<CharacterBasicDTO> dtos = new ArrayList<>();

        for (CharacterEntity entity : characterEntities){
            dtos.add(characterEntity2BasicDTO(entity));
        }

        return dtos;
    }

    private CharacterBasicDTO characterEntity2BasicDTO(CharacterEntity entity) {

        CharacterBasicDTO characterBasicDTO = new CharacterBasicDTO();

        characterBasicDTO.setId(entity.getId());
        characterBasicDTO.setImage(entity.getImage());
        characterBasicDTO.setName(entity.getName());

        return characterBasicDTO;
    }


    public Set<CharacterBasicDTO> characterEntitySet2BasicDTOSet(Set<CharacterEntity> characterEntities){

        Set<CharacterBasicDTO> dtos = new HashSet<>();

        for (CharacterEntity entity : characterEntities){
            dtos.add(characterEntity2BasicDTO(entity));
        }

        return dtos;
    }

    public Set<CharacterDTO> characterEntitySet2DTOSet(Set<CharacterEntity> characterEntities){

        Set<CharacterDTO> dtos = new HashSet<>();

        for (CharacterEntity entity : characterEntities){
            dtos.add(characterEntity2DTO(entity, true));
        }

        return dtos;
    }

    public void characterEntityRefreshValues(CharacterEntity entity, CharacterDTO characterDTO) {
        entity.setImage(characterDTO.getImage());
        entity.setWeight(characterDTO.getWeight());
        entity.setAge(characterDTO.getAge());
        entity.setHistory(characterDTO.getHistory());
    }

    public Set<CharacterDTO> iconEntitySet2DTOSet(Collection<CharacterEntity> entities, boolean loadPaises) {

        Set<CharacterDTO> dtos = new HashSet<>();

        for (CharacterEntity entity : entities) {
            dtos.add(this.characterEntity2DTO(entity, loadPaises));
        }

        return dtos;
    }


}