package com.maisonneuve.filmserie_api.repository;

import com.maisonneuve.filmserie_api.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre,Integer> {
}