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
    @PutMapping("/{contenuId}")
    public ProgressionSerie update(
            @PathVariable int contenuId,
            @RequestBody ProgressionSerie updated) {

        System.out.println("PUT progression reçu pour id = " + contenuId);

        ProgressionSerie existing = repo.findByContenuId(contenuId);

        if (existing == null) {
            System.out.println("Progression non trouvée !");
            throw new RuntimeException("Progression non trouvée");
        }

        existing.setEpisodesVus(updated.getEpisodesVus());
        existing.setEpisodesTotaux(updated.getEpisodesTotaux());

        return repo.save(existing);
    }

}