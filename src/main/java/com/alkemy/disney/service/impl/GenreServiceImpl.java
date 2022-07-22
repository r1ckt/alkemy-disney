package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.exception.ErrorEnum;
import com.alkemy.disney.exception.ParamNotFoundException;
import com.alkemy.disney.mapper.GenreMapper;
import com.alkemy.disney.repository.GenreRepository;
import com.alkemy.disney.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    private GenreMapper genreMapper;
    private GenreRepository genreRepository;

    @Autowired
    public GenreServiceImpl(GenreMapper genreMapper, GenreRepository genreRepository) {
        this.genreMapper = genreMapper;
        this.genreRepository = genreRepository;
    }

    @Override
    public GenreDTO save(@NotNull GenreDTO dto) {

        GenreEntity entity = genreMapper.genreDTO2Entity(dto);
        GenreEntity  entitySaved = genreRepository.save(entity);

        return genreMapper.genreEntity2DTO(entitySaved);
    }

    @Override
    public List<GenreDTO> getAllGenres() {

        List<GenreEntity> entities = genreRepository.findAll();

        return genreMapper.genreEntityList2DTOList(entities);
    }

    @Override
    public GenreDTO update(Long id, GenreDTO dto){

         GenreEntity entity = genreRepository.findById(id)
                 .orElseThrow(()-> new ParamNotFoundException(ErrorEnum.ID_GENRE_NOT_VALID.getMessage()
                 ));

        genreMapper.genreEntityRefreshValues(entity, dto);

        GenreEntity entitySaved = genreRepository.save(entity);

        return genreMapper.genreEntity2DTO(entitySaved);
    }
}
