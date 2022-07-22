package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.dto.CharacterFiltersDTO;
import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.exception.ErrorEnum;
import com.alkemy.disney.exception.ParamNotFoundException;
import com.alkemy.disney.mapper.CharacterMapper;
import com.alkemy.disney.repository.CharacterRepository;
import com.alkemy.disney.repository.specs.CharacterSpecification;
import com.alkemy.disney.service.CharacterService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public CharacterDTO update(@NonNull Long id, CharacterDTO dto){

        CharacterEntity entity = characterRepository.findById(id)
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_CHARACTER_NOT_VALID.getMessage()));

        characterMapper.characterEntityRefreshValues(entity, dto);
        CharacterEntity entitySaved = characterRepository.save(entity);

        return characterMapper.characterEntity2DTO(entitySaved,false);

    }

    public void delete(@NonNull Long id){
        characterRepository.findById(id)
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_CHARACTER_NOT_VALID.getMessage()));

        characterRepository.deleteById(id);
    }

    public CharacterDTO findById(@NonNull Long id) {

        CharacterEntity entity = characterRepository.findById(id)
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_CHARACTER_NOT_VALID.getMessage()));

        return characterMapper.characterEntity2DTO(entity,true);
    }

    @Override
    public List<CharacterBasicDTO> getByFilters(String name, Long age, Long weight, List<Long> movies) {

        CharacterFiltersDTO filtersDTO = new CharacterFiltersDTO(name, age, weight, movies);

        List<CharacterEntity> entityList = this.characterRepository.findAll(
                this.characterSpecification.getByFilters(filtersDTO)
        );

        return this.characterMapper.characterEntitySet2BasicDTOList(entityList);
    }

}
