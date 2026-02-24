package com.maisonneuve.filmserietrackerfx.model;

public class ProgressionSerie {

    private int contenuId;
    private int saisonsTotales;
    private int saisonsVues;
    private int episodesTotaux;
    private int episodesVus;

    public int getContenuId(){ return contenuId; }
    public void setContenuId(int id){ this.contenuId=id; }

    public int getSaisonsTotales(){ return saisonsTotales; }
    public void setSaisonsTotales(int v){ this.saisonsTotales=v; }

    public int getSaisonsVues(){ return saisonsVues; }
    public void setSaisonsVues(int v){ this.saisonsVues=v; }

    public int getEpisodesTotaux(){ return episodesTotaux; }
    public void setEpisodesTotaux(int v){ this.episodesTotaux=v; }

    public int getEpisodesVus(){ return episodesVus; }
    public void setEpisodesVus(int v){ this.episodesVus=v; }
}