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

    public CharacterEntity characterDTO2Entity(CharacterDTO characterDTO) {

        CharacterEntity characterEntity = new CharacterEntity();

        characterEntity.setImage(characterDTO.getImage());
        characterEntity.setName(characterDTO.getName());
        characterEntity.setAge(characterDTO.getAge());
        characterEntity.setWeight(characterDTO.getWeight());
        characterEntity.setHistory(characterDTO.getHistory());

        return characterEntity;
    }

    public CharacterDTO characterEntity2DTO(CharacterEntity entity, boolean loadMovies){

        CharacterDTO characterDTO = new CharacterDTO();

        characterDTO.setId(entity.getId());
        characterDTO.setImage(entity.getImage());
        characterDTO.setName(entity.getName());
        characterDTO.setAge(entity.getAge());
        characterDTO.setWeight(entity.getWeight());
        characterDTO.setHistory(entity.getHistory());

        if(loadMovies){
            List<MovieDTO> moviesDTO = this.movieMapper.movieEntityList2DTOList(entity.getMovies(), false);

            characterDTO.setMovies(moviesDTO);
        }

        return characterDTO;
    }

    public List<CharacterDTO> characterEntitySet2DTOList(Collection<CharacterEntity> characters,
                                                         boolean loadMovies) {

        List<CharacterDTO> characterDTOS = new ArrayList<>();

        for (CharacterEntity dto : characters) {
            characterDTOS.add(this.characterEntity2DTO(dto, loadMovies));
        }

        return characterDTOS;
    }

    public Set<CharacterEntity> characterDTOList2Entity(List<CharacterDTO> dtos) {

        Set<CharacterEntity> characters = new HashSet<>();

        for (CharacterDTO dto : dtos) {
            characters.add(this.characterDTO2Entity(dto));
        }

        return characters;
    }

    private CharacterBasicDTO characterEntity2BasicDTO(CharacterEntity entity) {

        CharacterBasicDTO characterBasicDTO = new CharacterBasicDTO();

        characterBasicDTO.setId(entity.getId());
        characterBasicDTO.setImage(entity.getImage());
        characterBasicDTO.setName(entity.getName());

        return characterBasicDTO;
    }


    public List<CharacterBasicDTO> characterEntitySet2BasicDTOList(Collection<CharacterEntity> characterEntities){

        List<CharacterBasicDTO> dtos = new ArrayList<>();

        for (CharacterEntity entity : characterEntities){
            dtos.add(characterEntity2BasicDTO(entity));
        }

        return dtos;
    }

    public void characterEntityRefreshValues(CharacterEntity entity, CharacterDTO characterDTO) {

        entity.setImage(characterDTO.getImage());
        entity.setWeight(characterDTO.getWeight());
        entity.setAge(characterDTO.getAge());
        entity.setHistory(characterDTO.getHistory());

    }

}