package com.maisonneuve.filmserie_api.repository;


import com.maisonneuve.filmserie_api.model.Contenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContenuRepository extends JpaRepository<Contenu, Integer> {
}