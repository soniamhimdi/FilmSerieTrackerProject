package com.maisonneuve.filmserietrackerfx.controller;

import com.maisonneuve.filmserietrackerfx.model.Contenu;
import com.maisonneuve.filmserietrackerfx.model.Genre;
import com.maisonneuve.filmserietrackerfx.model.ProgressionSerie;
import com.maisonneuve.filmserietrackerfx.service.ApiService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ContenuController {

    @FXML private TableView<Contenu> tableContenus;
    @FXML private TableColumn<Contenu, String> colTitre;
    @FXML private TableColumn<Contenu, Integer> colAnnee;
    @FXML private TableColumn<Contenu, String> colType;
    @FXML private TableColumn<Contenu, String> colGenre;
    @FXML private TableColumn<Contenu, String> colStatut;
    @FXML private TableColumn<Contenu, Boolean> colWatchlist;
    @FXML private TableColumn<Contenu, Integer> colNote;
    @FXML private TableColumn<Contenu,String> colProgress;

    @FXML private TextField titreField;
    @FXML private TextField anneeField;

    @FXML private ComboBox<String> typeCombo;
    @FXML private ComboBox<String> statutCombo;
    @FXML private CheckBox watchlistCheck;
    @FXML private ComboBox<Integer> noteCombo;
    @FXML private ComboBox<Genre> genreCombo;
    @FXML private TextField searchField;

    @FXML private Label totalLabel;
    @FXML private Label filmsLabel;
    @FXML private Label seriesLabel;
    @FXML private Label watchlistLabel;

    @FXML private VBox progressionBox;
    @FXML private TextField episodesVusField;
    @FXML private TextField episodesTotalField;

    private final ApiService apiService = new ApiService();
    private Map<Integer,String> genreMap = new HashMap<>();

    @FXML
    public void initialize() {

        colTitre.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getTitre()));

        colAnnee.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getAnneeSortie()).asObject());

        colType.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getType()));

        colStatut.setCellValueFactory(c ->
                new javafx.beans.property.SimpleStringProperty(c.getValue().getStatut()));

        colWatchlist.setCellValueFactory(c ->
                new javafx.beans.property.SimpleBooleanProperty(c.getValue().isWatchlist()));

        colNote.setCellValueFactory(c ->
                new javafx.beans.property.SimpleIntegerProperty(c.getValue().getNote()).asObject());

        // GENRE MAP
        for(Genre g : apiService.getAllGenres()){
            genreMap.put(g.getId(), g.getNom());
        }

        colGenre.setCellValueFactory(c -> {
            int genreId = c.getValue().getGenreId();
            String nom = genreMap.getOrDefault(genreId,"");
            return new javafx.beans.property.SimpleStringProperty(nom);
        });

        typeCombo.getItems().addAll("FILM", "SERIE");
        statutCombo.getItems().addAll("A_VOIR","EN_COURS","VU");
        noteCombo.getItems().addAll(0,1,2,3,4,5);
// valeur par defaut
        typeCombo.setValue("FILM");
        statutCombo.setValue("A_VOIR");
        noteCombo.setValue(0);

        genreCombo.setItems(
                FXCollections.observableArrayList(
                        apiService.getAllGenres()
                )
        );
        //rechercher
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {

            tableContenus.setItems(
                    FXCollections.observableArrayList(
                            apiService.getAllContenus()
                                    .stream()
                                    .filter(c ->
                                            c.getTitre().toLowerCase()
                                                    .contains(newVal.toLowerCase())
                                    )
                                    .toList()
                    )
            );

        });
        //progression Serie
        colProgress.setCellValueFactory(c -> {

            if(!"SERIE".equals(c.getValue().getType()))
                return new javafx.beans.property.SimpleStringProperty("");

            ProgressionSerie p =
                    apiService.getProgression(c.getValue().getId());

            if(p==null) return
                    new javafx.beans.property.SimpleStringProperty("0%");

            double percent =
                    (double)p.getEpisodesVus() /
                            p.getEpisodesTotaux()*100;

            return new javafx.beans.property.SimpleStringProperty(
                    String.format("%.0f %%",percent));
        });
        typeCombo.valueProperty().addListener((obs, oldVal, newVal) -> {

            boolean isSerie = "SERIE".equals(newVal);

            progressionBox.setVisible(isSerie);
            progressionBox.setManaged(isSerie);

        });

        chargerContenus(); // toujours à la fin
    }


    public void chargerContenus(){
        tableContenus.setItems(
                FXCollections.observableArrayList(apiService.getAllContenus())
        );
        updateStats();
    }

    @FXML
    public void ajouterContenu(){

        Contenu c = new Contenu();
        c.setTitre(titreField.getText());
        c.setAnneeSortie(Integer.parseInt(anneeField.getText()));

        c.setType(typeCombo.getValue());
        c.setStatut(statutCombo.getValue());
        c.setWatchlist(watchlistCheck.isSelected());
        c.setNote(noteCombo.getValue());
        c.setGenreId(genreCombo.getValue().getId());

        Contenu saved = apiService.ajouterContenu(c);

        if(saved != null && "SERIE".equals(saved.getType())){

            ProgressionSerie p = new ProgressionSerie();

            p.setContenuId(saved.getId());

            p.setEpisodesVus(
                    Integer.parseInt(episodesVusField.getText())
            );

            p.setEpisodesTotaux(
                    Integer.parseInt(episodesTotalField.getText())
            );

            apiService.saveProgression(p);
        }

        chargerContenus();
    }

    @FXML
    public void modifierContenu(){

        Contenu c = tableContenus.getSelectionModel().getSelectedItem();
        if(c == null) return;

        c.setTitre(titreField.getText());
        c.setAnneeSortie(Integer.parseInt(anneeField.getText()));

        c.setType(typeCombo.getValue());
        c.setStatut(statutCombo.getValue());
        c.setWatchlist(watchlistCheck.isSelected());
        c.setNote(noteCombo.getValue());
        c.setGenreId(genreCombo.getValue().getId());
        apiService.modifierContenu(c);
        chargerContenus();
    }

    @FXML
    public void supprimerContenu(){

        Contenu c = tableContenus.getSelectionModel().getSelectedItem();
        if(c == null) return;

        apiService.supprimerContenu(c.getId());
        chargerContenus();
    }

    private void updateStats(){

        var list = apiService.getAllContenus();

        long total = list.size();
        long films = list.stream()
                .filter(c -> "FILM".equals(c.getType()))
                .count();

        long series = list.stream()
                .filter(c -> "SERIE".equals(c.getType()))
                .count();

        long watchlist = list.stream()
                .filter(Contenu::isWatchlist)
                .count();

        totalLabel.setText(String.valueOf(total));
        filmsLabel.setText(String.valueOf(films));
        seriesLabel.setText(String.valueOf(series));
        watchlistLabel.setText(String.valueOf(watchlist));
    }
}