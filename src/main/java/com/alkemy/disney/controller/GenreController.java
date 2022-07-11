package com.alkemy.disney.controller;

import com.alkemy.disney.dto.GenreDTO;
import com.alkemy.disney.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping
    public ResponseEntity<GenreDTO> save(@RequestBody GenreDTO genreDTO){
        GenreDTO genreSaved = genreService.save(genreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(genreSaved);
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAll() {
        List<GenreDTO> genres = genreService.getAllGenres();
        return ResponseEntity.ok().body(genres);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GenreDTO update(@PathVariable Long id, @RequestBody GenreDTO genreDTO){
        return genreService.update(id, genreDTO);
    }

}
