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



    public CharacterDTO characterEntity2DTO(CharacterEntity characterEntity, boolean loadMovies){

        CharacterDTO characterDTO = new CharacterDTO();

        characterDTO.setId(characterEntity.getId());
        characterDTO.setImage(characterEntity.getImage());
        characterDTO.setName(characterEntity.getName());
        characterDTO.setAge(characterEntity.getAge());
        characterDTO.setWeight(characterEntity.getWeight());
        characterDTO.setHistory(characterEntity.getHistory());

        if(loadMovies){
            List<MovieDTO> moviesDTO = this.movieMapper.movieEntityList2DTOList(characterEntity.getMovies(), false);

            characterDTO.setMovies(moviesDTO);
        }

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


    public List<CharacterBasicDTO> characterEntitySet2BasicDTOList(Collection<CharacterEntity> characterEntities){

        List<CharacterBasicDTO> dtos = new ArrayList<>();

        for (CharacterEntity entity : characterEntities){
            dtos.add(characterEntity2BasicDTO(entity));
        }

        return dtos;
    }

    public List<CharacterBasicDTO> characterEntitySet2DTOList(Set<CharacterEntity> characterEntities){

        List<CharacterBasicDTO> dtos = new ArrayList<>();
        CharacterBasicDTO characterBasicDTO = new CharacterBasicDTO();


        for (CharacterEntity entity : characterEntities){

            characterBasicDTO.setId(entity.getId());
            characterBasicDTO.setImage(entity.getImage());
            characterBasicDTO.setName(entity.getName());

            dtos.add(characterBasicDTO);
        }

        return dtos;
    }

    public void characterEntityRefreshValues(CharacterEntity entity, CharacterDTO characterDTO) {
        entity.setImage(characterDTO.getImage());
        entity.setWeight(characterDTO.getWeight());
        entity.setAge(characterDTO.getAge());
        entity.setHistory(characterDTO.getHistory());
    }


    public List<CharacterDTO> characterEntitySet2DTOList(
                                                Collection<CharacterEntity> characters,
                                                boolean loadMovies) {

        List<CharacterDTO> characterDTOS = new ArrayList<>();

        for (CharacterEntity dto : characters) {
            characterDTOS.add(this.characterEntity2DTO(dto, loadMovies));
        }

        return characterDTOS;
    }

    public Set<CharacterEntity> characterDTOList2Entity(List<CharacterDTO> characterDTOS) {

        Set<CharacterEntity> characters = new HashSet<>();

        for (CharacterDTO dto : characterDTOS) {
            characters.add(this.characterDTO2Entity(dto));
        }
        return characters;
    }
}