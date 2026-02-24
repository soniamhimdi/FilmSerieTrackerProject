package com.maisonneuve.filmserie_api.controller;
import com.maisonneuve.filmserie_api.model.Contenu;
import com.maisonneuve.filmserie_api.repository.ContenuRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contenus")
@CrossOrigin
public class ContenuController {

    private final ContenuRepository repo;

    public ContenuController(ContenuRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Contenu> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Contenu add(@RequestBody Contenu c) {
        return repo.save(c);
    }

    @PutMapping("/{id}")
    public Contenu update(@PathVariable int id, @RequestBody Contenu c) {
        c.setId(id);
        return repo.save(c);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        repo.deleteById(id);
    }
}