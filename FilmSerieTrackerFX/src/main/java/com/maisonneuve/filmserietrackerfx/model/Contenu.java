package com.maisonneuve.filmserietrackerfx.model;

public class Contenu {
    private int id;
    private String titre;
    private String type;
    private int anneeSortie;
    private String realisateur;
    private String synopsis;
    private int genreId;
    private String statut;
    private int note;
    private boolean watchlist;

    public Contenu() {
    }

    public Contenu(int id, String titre, String type, int anneeSortie, String realisateur, String synopsis, int genreId, String statut, int note, boolean watchlist) {
        this.id = id;
        this.titre = titre;
        this.type = type;
        this.anneeSortie = anneeSortie;
        this.realisateur = realisateur;
        this.synopsis = synopsis;
        this.genreId = genreId;
        this.statut = statut;
        this.note = note;
        this.watchlist = watchlist;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAnneeSortie() {
        return anneeSortie;
    }

    public void setAnneeSortie(int anneeSortie) {
        this.anneeSortie = anneeSortie;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(String realisateur) {
        this.realisateur = realisateur;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(boolean watchlist) {
        this.watchlist = watchlist;
    }
}
