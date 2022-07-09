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
    @Lazy
    private MovieMapper movieMapper;

    public CharacterEntity characterDTO2Entity(CharacterDTO characterDTO, boolean loadMovies) {

        CharacterEntity characterEntity = new CharacterEntity();

        characterEntity.setImage(characterDTO.getImage());
        characterEntity.setName(characterDTO.getName());
        characterEntity.setAge(characterDTO.getAge());
        characterEntity.setWeight(characterDTO.getWeight());
        characterEntity.setHistory(characterDTO.getHistory());
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
        characterBasicDTO.setImage(entity.getImage());
        characterBasicDTO.setName(entity.getName());
        return characterBasicDTO;
    }


    public List<CharacterBasicDTO> characterEntitySet2BasicDTOSet(List<CharacterEntity> characterEntities){
        List<CharacterBasicDTO> dtos = new ArrayList<>();
        for (CharacterEntity entity : characterEntities){
            dtos.add(characterEntity2BasicDTO(entity));
        }
        return dtos;
    }

    public Set<CharacterEntity> dtoSet2EntitySet(Set<CharacterDTO> dtoSet, boolean loadMovies) {
        Set<CharacterEntity> entitySet = new HashSet<>();
        for (CharacterDTO dto : dtoSet){
            entitySet.add(this.characterDTO2Entity(dto,true));
        }
        return entitySet;
    }
    public CharacterEntity characterEntityRefreshValues(CharacterEntity personajeEntity, CharacterDTO personajeDT0) {
        personajeEntity.setImage(personajeDT0.getImage());
        personajeEntity.setName(personajeDT0.getName());
        personajeEntity.setAge(personajeDT0.getAge());
        personajeEntity.setWeight(personajeDT0.getWeight());
        personajeEntity.setHistory(personajeDT0.getHistory());
        personajeEntity.setId(personajeDT0.getId());
        return personajeEntity;
    }

}