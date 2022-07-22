package com.alkemy.disney.mapper;

import com.alkemy.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.entity.CharacterEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharacterMapper {

    @Autowired
    private MovieMapper movieMapper;

    public CharacterEntity characterDTO2Entity(CharacterDTO dto) {

        CharacterEntity entity = new CharacterEntity();

        entity.setImage(dto.getImage());
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setWeight(dto.getWeight());
        entity.setHistory(dto.getHistory());

        return entity;
    }

    public CharacterDTO characterEntity2DTO(CharacterEntity entity, boolean loadMovies){

        CharacterDTO dto = new CharacterDTO();

        dto.setId(entity.getId());
        dto.setImage(entity.getImage());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setWeight(entity.getWeight());
        dto.setHistory(entity.getHistory());

        if(loadMovies){
            List<MovieDTO> moviesDTO = this.movieMapper.movieEntityList2DTOList(entity.getMovies(), false);

            dto.setMovies(moviesDTO);
        }

        return dto;
    }

    public List<CharacterDTO> characterEntitySet2DTOList(Collection<CharacterEntity> entityList, boolean loadMovies) {

        List<CharacterDTO> dtos = new ArrayList<>();

        for (CharacterEntity entity : entityList) {
            dtos.add(this.characterEntity2DTO(entity, loadMovies));
        }

        return dtos;
    }

    public Set<CharacterEntity> characterDTOList2Entity(List<CharacterDTO> dtos) {

        Set<CharacterEntity> entitySet = new HashSet<>();

        for (CharacterDTO dto : dtos) {
            entitySet.add(this.characterDTO2Entity(dto));
        }

        return entitySet;
    }

    private CharacterBasicDTO characterEntity2BasicDTO(CharacterEntity entity) {

        CharacterBasicDTO dto = new CharacterBasicDTO();


        dto.setImage(entity.getImage());
        dto.setName(entity.getName());

        return dto;
    }


    public List<CharacterBasicDTO> characterEntitySet2BasicDTOList(Collection<CharacterEntity> entityList){

        List<CharacterBasicDTO> dtoList = new ArrayList<>();

        for (CharacterEntity entity : entityList){
            dtoList.add(characterEntity2BasicDTO(entity));
        }

        return dtoList;
    }

    public void characterEntityRefreshValues(CharacterEntity entity, CharacterDTO dto) {

        entity.setName(dto.getName());
        entity.setImage(dto.getImage());
        entity.setWeight(dto.getWeight());
        entity.setAge(dto.getAge());
        entity.setHistory(dto.getHistory());

    }

}