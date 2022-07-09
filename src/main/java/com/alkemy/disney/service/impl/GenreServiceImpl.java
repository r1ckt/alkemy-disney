package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.exception.ParamNotFoundException;
import com.alkemy.disney.mapper.GenreMapper;
import com.alkemy.disney.repository.GenreRepository;
import com.alkemy.disney.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreMapper genreMapper;

    @Autowired
    private GenreRepository genreRepository;

    @Override
    public GenreDTO save(GenreDTO genreDTO) {

        GenreEntity entity = genreMapper.genreDTO2Entity(genreDTO);
        GenreEntity  entitySaved = genreRepository.save(entity);

        GenreDTO result = genreMapper.genreEntity2DTO(entitySaved);

        return result;
    }

    @Override
    public List<GenreDTO> getAllGenres() {
        List<GenreEntity> entities = genreRepository.findAll();
        List<GenreDTO> result = genreMapper.genreEntityList2DTOList(entities);
        return result;
    }

    @Override
    public void delete(Long id) {
        this.genreRepository.deleteById(id);
    }

    @Override
    public GenreDTO update(Long id, GenreDTO genreDTO){
        Optional<GenreEntity> entity = genreRepository.findById(id);
        if (!entity.isPresent()) {
            throw new ParamNotFoundException("Error: Invalid character id");
        }

        genreMapper.genreEntityRefreshValues(entity.get(), genreDTO);
        GenreEntity entitySaved = genreRepository.save(entity.get());
        return genreMapper.genreEntity2DTO(entitySaved);
    }
}
