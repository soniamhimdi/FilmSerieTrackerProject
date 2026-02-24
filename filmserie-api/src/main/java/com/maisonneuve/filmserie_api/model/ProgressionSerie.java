package com.maisonneuve.filmserie_api.model;

import jakarta.persistence.*;

@Entity
@Table(name="progression_series")
public class ProgressionSerie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int contenuId;
    private int saisonsTotales;
    private int saisonsVues;
    private int episodesTotaux;
    private int episodesVus;

    public int getId(){ return id; }
    public void setId(int id){ this.id=id; }

    public int getContenuId(){ return contenuId; }
    public void setContenuId(int contenuId){ this.contenuId=contenuId; }

    public int getSaisonsTotales(){ return saisonsTotales; }
    public void setSaisonsTotales(int v){ this.saisonsTotales=v; }

    public int getSaisonsVues(){ return saisonsVues; }
    public void setSaisonsVues(int v){ this.saisonsVues=v; }

    public int getEpisodesTotaux(){ return episodesTotaux; }
    public void setEpisodesTotaux(int v){ this.episodesTotaux=v; }

    public int getEpisodesVus(){ return episodesVus; }
    public void setEpisodesVus(int v){ this.episodesVus=v; }
}