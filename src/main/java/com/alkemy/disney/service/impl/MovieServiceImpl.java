package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.MovieFiltersDTO;
import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.entity.MovieEntity;
import com.alkemy.disney.exception.ParamNotFoundException;
import com.alkemy.disney.mapper.MovieMapper;
import com.alkemy.disney.repository.CharacterRepository;
import com.alkemy.disney.repository.MovieRepository;
import com.alkemy.disney.repository.specs.MovieSpecification;
import com.alkemy.disney.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieMapper movieMapper;
    private MovieRepository movieRepository;
    private CharacterRepository characterRepository;
    private MovieSpecification movieSpecification;

    @Autowired
    public MovieServiceImpl(MovieMapper movieMapper,
                            MovieRepository movieRepository,
                            CharacterRepository characterRepository,
                            MovieSpecification movieSpecification) {

        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
        this.movieSpecification = movieSpecification;
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

        return movieMapper.movieEntityList2DTOList(movieEntities, true);
    }

    @Override
    public void delete(Long id) {
        this.movieRepository.deleteById(id);
    }

    @Override
    public MovieDTO update(Long id, MovieDTO dto){

        Optional<MovieEntity> entity = movieRepository.findById(id);

        if (entity.isEmpty()) {
            throw new ParamNotFoundException("Error: Invalid movie id!!");
        }

        movieMapper.movieEntityRefreshValues(entity.get(), dto);

        MovieEntity entitySaved = movieRepository.save(entity.get());

        return movieMapper.movieEntity2DTO(entitySaved, false);
    }

    @Override
    public void addCharacter(Long idMovie, Long idCharacter) {

        CharacterEntity character = this.characterRepository.findById(idCharacter).get();
        MovieEntity movie = movieRepository.findById(idMovie).get();

        movie.addCharacter(character);

        this.movieRepository.save(movie);
    }

    @Override
    public void removeCharacter(Long idMovie, Long idCharacter) {

        CharacterEntity character = this.characterRepository.findById(idCharacter).get();
        MovieEntity movie = movieRepository.findById(idMovie).get();

        movie.removeCharacter(character);

        this.movieRepository.save(movie);
    }

    @Override
    public List<MovieDTO> getMovieByFilters(String title, String creationDate, Long genreId, String order) {

        MovieFiltersDTO filtersDTO = new MovieFiltersDTO(title, creationDate, genreId, order);

        List<MovieEntity> movies = this.movieRepository.findAll(
                this.movieSpecification.getByFilters(filtersDTO)
        );

        List<MovieDTO> dtos = this.movieMapper.movieEntityList2DTOList(movies, true);

        return dtos;
    }

    @Override
    public MovieDTO updateMovie(Long id, MovieDTO dto) {
        Optional<MovieEntity> movie = this.movieRepository.findById(id);

        if (movie.isEmpty()) {
            throw new ParamNotFoundException("That Movie id was not found!");
        }

        this.movieMapper.movieEntityRefreshValues(movie.get(), dto);

        MovieEntity movieSaved = this.movieRepository.save(movie.get());
        MovieDTO result = this.movieMapper.movieEntity2DTO(movieSaved, false);

        return result;
    }


}
