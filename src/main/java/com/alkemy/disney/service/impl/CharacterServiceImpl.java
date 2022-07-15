package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.CharacterFiltersDTO;
import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.exception.ParamNotFoundException;
import com.alkemy.disney.mapper.CharacterMapper;
import com.alkemy.disney.repository.CharacterRepository;
import com.alkemy.disney.repository.specs.CharacterSpecification;
import com.alkemy.disney.service.CharacterService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CharacterServiceImpl implements CharacterService {

    private CharacterMapper characterMapper;
    private CharacterRepository characterRepository;
    private CharacterSpecification characterSpecification;

    @Autowired
    public CharacterServiceImpl(CharacterMapper characterMapper,
                                CharacterRepository characterRepository,
                                CharacterSpecification characterSpecification) {

        this.characterMapper = characterMapper;
        this.characterRepository = characterRepository;
        this.characterSpecification = characterSpecification;
    }

    public CharacterDTO save(CharacterDTO characterDTO){

        CharacterEntity entity = characterMapper.characterDTO2Entity(characterDTO);
        CharacterEntity entitySaved = characterRepository.save(entity);

        return characterMapper.characterEntity2DTO(entitySaved,false);
    }

    public CharacterDTO update(Long id, CharacterDTO characterDTO){

        Optional<CharacterEntity> entity = characterRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ParamNotFoundException("Error: Invalid character id");
        }

        characterMapper.characterEntityRefreshValues(entity.get(), characterDTO);
        CharacterEntity entitySaved = characterRepository.save(entity.get());

        return characterMapper.characterEntity2DTO(entitySaved,false);

    }

    public void delete(@NonNull Long id){
        characterRepository.deleteById(id);
    }

    public CharacterDTO findById(@NonNull Long id) {

        Optional<CharacterEntity> entity = characterRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ParamNotFoundException("Error: Invalid character id.");
        }

        return characterMapper.characterEntity2DTO(entity.get(),true);
    }

    @Override
    public List<CharacterDTO> getByFilters(String name, Long age, Long weight, List<Long> movies) {

        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, weight, movies);

        List<CharacterEntity> entityList = this.characterRepository.findAll(
                this.characterSpecification.getByFilters(filtersDTO)
        );

        List<CharacterDTO> dtos = this.characterMapper.characterEntitySet2DTOList(entityList, true);

        return dtos;
    }

    public List<CharacterBasicDTO> getAll(){

        List<CharacterEntity> entities = characterRepository.findAll();

        return this.characterMapper.characterEntitySet2BasicDTOList(entities);
    }

}
