package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.entity.GenreEntity;
import com.alkemy.disney.entity.MovieEntity;
import com.alkemy.disney.exception.ParamNotFoundException;
import com.alkemy.disney.mapper.CharacterMapper;
import com.alkemy.disney.mapper.MovieMapper;
import com.alkemy.disney.repository.MovieRepository;
import com.alkemy.disney.service.CharacterService;
import com.alkemy.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieMapper movieMapper;
    private MovieRepository movieRepository;
    private CharacterMapper characterMapper;
    private CharacterService characterService;

    @Autowired
    public MovieServiceImpl(MovieMapper movieMapper,
                            MovieRepository movieRepository,
                            CharacterMapper characterMapper,
                            CharacterService characterService) {

        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
        this.characterMapper = characterMapper;
        this.characterService = characterService;
    }

    @Override
    public MovieDTO create(MovieDTO movieDTO) {

        MovieEntity movieEntity = movieMapper.movieDTO2Entity(movieDTO);
        MovieEntity savedMovie = movieRepository.save(movieEntity);

        return movieMapper.movieEntity2DTO(savedMovie, false);
    }

    @Override
    public List<MovieDTO> getAllMovies() {
        List<MovieEntity> movieEntities = movieRepository.findAll();

        return movieMapper.entitySet2DtoList(movieEntities);
    }

    @Override
    public void delete(Long id) {
        this.movieRepository.deleteById(id);
    }

    @Override
    public MovieDTO update(Long id, MovieDTO movieDTO){

        Optional<MovieEntity> entity = movieRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ParamNotFoundException("Error: Invalid movie id");
        }

        movieMapper.movieEntityRefreshValues(entity.get(), movieDTO);

        MovieEntity entitySaved = movieRepository.save(entity.get());

        return movieMapper.movieEntity2DTO(entitySaved, false);
    }

}
