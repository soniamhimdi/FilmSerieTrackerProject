package com.maisonneuve.filmserie_api.controller;

import com.maisonneuve.filmserie_api.model.ProgressionSerie;
import com.maisonneuve.filmserie_api.repository.ProgressionSerieRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progression")
@CrossOrigin
public class ProgressionSerieController {

    private final ProgressionSerieRepository repo;

    public ProgressionSerieController(ProgressionSerieRepository repo){
        this.repo=repo;
    }

    @GetMapping("/{id}")
    public ProgressionSerie get(@PathVariable int id){
        return repo.findByContenuId(id);
    }

    @PostMapping
    public ProgressionSerie save(@RequestBody ProgressionSerie p){
        return repo.save(p);
    }


}