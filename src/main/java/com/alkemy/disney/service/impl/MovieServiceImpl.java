package com.alkemy.disney.service.impl;

import com.alkemy.disney.dto.MovieBasicDTO;
import com.alkemy.disney.dto.MovieDTO;
import com.alkemy.disney.dto.MovieFiltersDTO;
import com.alkemy.disney.entity.CharacterEntity;
import com.alkemy.disney.entity.MovieEntity;
import com.alkemy.disney.exception.ErrorEnum;
import com.alkemy.disney.exception.ParamNotFoundException;
import com.alkemy.disney.mapper.MovieMapper;
import com.alkemy.disney.repository.CharacterRepository;
import com.alkemy.disney.repository.GenreRepository;
import com.alkemy.disney.repository.MovieRepository;
import com.alkemy.disney.repository.specs.MovieSpecification;
import com.alkemy.disney.service.MovieService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private MovieMapper movieMapper;
    private MovieRepository movieRepository;
    private CharacterRepository characterRepository;
    private MovieSpecification movieSpecification;
    private GenreRepository genreRepository;

    @Autowired
    public MovieServiceImpl(MovieMapper movieMapper,
                            MovieRepository movieRepository,
                            CharacterRepository characterRepository,
                            MovieSpecification movieSpecification,
                            GenreRepository genreRepository) {

        this.movieMapper = movieMapper;
        this.movieRepository = movieRepository;
        this.characterRepository = characterRepository;
        this.movieSpecification = movieSpecification;
        this.genreRepository = genreRepository;
    }

    @Override
    public MovieDTO create(MovieDTO dto) {

        genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_GENRE_NOT_VALID.getMessage()));

        MovieEntity movieEntity = movieMapper.movieDTO2Entity(dto);
        MovieEntity savedMovie = movieRepository.save(movieEntity);

        return movieMapper.movieEntity2DTO(savedMovie, true);
    }


    @Override
    public void delete(@NotNull Long id) {

        movieRepository.findById(id)
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_MOVIE_NOT_VALID.getMessage()));

        movieRepository.deleteById(id);
    }

    @Override
    public MovieDTO update(@NotNull Long id,
                           @NotNull MovieDTO dto){

        genreRepository.findById(dto.getGenreId())
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_GENRE_NOT_VALID.getMessage()));

        MovieEntity entity = movieRepository.findById(id)
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_MOVIE_NOT_VALID.getMessage()));


        movieMapper.movieEntityRefreshValues(entity, dto);

        MovieEntity entitySaved = movieRepository.save(entity);

        return movieMapper.movieEntity2DTO(entitySaved, true);
    }

    @Override
    public void addCharacter(@NotNull Long idMovie,
                             @NotNull Long idCharacter) {

        CharacterEntity character = this.characterRepository.findById(idCharacter)
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_CHARACTER_NOT_VALID.getMessage()));


        MovieEntity movie = movieRepository.findById(idMovie)
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_MOVIE_NOT_VALID.getMessage()));

        movie.addCharacter(character);

        this.movieRepository.save(movie);
    }

    @Override
    public void removeCharacter(@NotNull Long idMovie,
                                @NotNull Long idCharacter) {

        CharacterEntity character = this.characterRepository.findById(idCharacter)
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_CHARACTER_NOT_VALID.getMessage()));

        MovieEntity movie = movieRepository.findById(idMovie)
                .orElseThrow(() -> new ParamNotFoundException(ErrorEnum.ID_MOVIE_NOT_VALID.getMessage()));

        movie.removeCharacter(character);

        this.movieRepository.save(movie);
    }

    @Override
    public List<MovieBasicDTO> getMovieByFilters(String title, String creationDate, Long genreId, String order) {

        MovieFiltersDTO filtersDTO = new MovieFiltersDTO(title, creationDate, genreId, order);

        List<MovieEntity> movies = this.movieRepository.findAll(
                this.movieSpecification.getByFilters(filtersDTO)
        );

        return this.movieMapper.movieEntityList2DTOList(movies);
    }




}
