package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.entity.MovieEntity;
import com.alkemy.disney.mapper.CharacterMapper;
import com.alkemy.disney.mapper.MovieMapper;
import com.alkemy.disney.repository.MovieRepository;
import com.alkemy.disney.service.CharacterService;
import com.alkemy.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CharacterMapper characterMapper;

    @Autowired
    private CharacterService characterService;

    @Override
    public MovieDTO create(MovieDTO movieDTO) {

        MovieEntity movieEntity = movieMapper.movieDTO2Entity(movieDTO, true);
        MovieEntity savedMovie = movieRepository.save(movieEntity);

        return movieMapper.movieEntity2DTO(savedMovie, false);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<MovieEntity> movieEntities = movieRepository.findAll();

        return movieMapper.entitySet2DtoList(movieEntities, false);
    }

    @Override
    public void delete(Long id) {
        // TODO : finish delete method at MovieServiceImpl
    }


}
