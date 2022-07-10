package com.alkemy.disney.controller;

import com.alkemy.disney.dto.CharacterBasicDTO;
import com.alkemy.disney.dto.CharacterDTO;
import com.alkemy.disney.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @PostMapping
    public ResponseEntity<CharacterDTO> save(@RequestBody CharacterDTO characterDTO){
        CharacterDTO characterSaved = characterService.save(characterDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(characterSaved);
    }

    @GetMapping
    public ResponseEntity<List<CharacterBasicDTO>> getAll() {
        List<CharacterBasicDTO> characters  = characterService.getAll();
        return ResponseEntity.ok().body(characters);
    }
}
