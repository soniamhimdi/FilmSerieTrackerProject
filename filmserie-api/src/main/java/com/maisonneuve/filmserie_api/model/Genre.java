package com.maisonneuve.filmserie_api.model;

import jakarta.persistence.*;

@Entity
@Table(name="genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nom;

    public int getId(){ return id; }
    public void setId(int id){ this.id=id; }

    public String getNom(){ return nom; }
    public void setNom(String nom){ this.nom=nom; }
}