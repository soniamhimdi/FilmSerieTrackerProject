package com.maisonneuve.filmserie_api.controller;

import com.maisonneuve.filmserie_api.model.Genre;
import com.maisonneuve.filmserie_api.repository.GenreRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@CrossOrigin
public class GenreController {

    private final GenreRepository repo;

    public GenreController(GenreRepository repo){
        this.repo=repo;
    }

    @GetMapping
    public List<Genre> getAll(){
        return repo.findAll();
    }
}