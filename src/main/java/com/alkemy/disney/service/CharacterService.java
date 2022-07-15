package com.alkemy.disney.service;

import com.alkemy.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.dto.CharacterDTO;
import lombok.NonNull;

import java.util.List;

public interface CharacterService {
    CharacterDTO save(CharacterDTO characterDTO);
    CharacterDTO update(Long id, CharacterDTO characterDTO);
    void delete(@NonNull Long id);
    List<CharacterBasicDTO> getAll();
    CharacterDTO findById(@NonNull Long id);

    List<CharacterDTO> getByFilters(String name, Long age, Long weight, List<Long> movies);
}
