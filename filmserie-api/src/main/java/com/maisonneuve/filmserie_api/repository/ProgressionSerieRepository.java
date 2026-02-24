package com.maisonneuve.filmserie_api.repository;

import com.maisonneuve.filmserie_api.model.ProgressionSerie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressionSerieRepository
        extends JpaRepository<ProgressionSerie,Integer> {

    ProgressionSerie findByContenuId(int contenuId);
}