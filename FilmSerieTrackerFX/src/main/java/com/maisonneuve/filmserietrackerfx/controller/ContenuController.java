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
import javafx.animation.*;
import javafx.util.Duration;
import javafx.scene.layout.StackPane;

public class ContenuController {

    @FXML private TableView<Contenu> tableContenus;
    @FXML private TableColumn<Contenu, String> colTitre;
    @FXML private TableColumn<Contenu, Integer> colAnnee;
    @FXML private TableColumn<Contenu, String> colType;
    @FXML private TableColumn<Contenu, String> colGenre;
    @FXML private TableColumn<Contenu, String> colStatut;
    @FXML private TableColumn<Contenu, Boolean> colWatchlist;
    @FXML private TableColumn<Contenu, Integer> colNote;
    @FXML private TableColumn<Contenu,Double> colProgress;

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
    // Vbox progression
    @FXML private VBox progressionBox;
    @FXML private TextField episodesVusField;
    @FXML private TextField episodesTotalField;
    //progress bar
    @FXML private ProgressBar serieProgressBar;
    @FXML private Label progressLabel;
    //toast
    @FXML private Label toastLabel;
    @FXML private StackPane toastContainer;

    private final ApiService apiService = new ApiService();
    private Map<Integer,String> genreMap = new HashMap<>();

    private void updateProgress() {

        try {
            int vus = Integer.parseInt(episodesVusField.getText());
            int total = Integer.parseInt(episodesTotalField.getText());

            if (total > 0) {
                double progress = (double) vus / total;
                animateProgress(progress);
            } else {
                serieProgressBar.setProgress(0);
                progressLabel.setText("0%");
            }

        } catch (NumberFormatException e) {
            serieProgressBar.setProgress(0);
            progressLabel.setText("0%");
        }
    }
    private void animateProgress(double newValue) {

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(400),
                        new KeyValue(
                                serieProgressBar.progressProperty(),
                                newValue
                        )
                )
        );

        timeline.play();

        double percent = newValue * 100;
        progressLabel.setText(String.format("%.0f%%", percent));
    }

    @FXML
    public void initialize() {

        // recharge tableau

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
        episodesVusField.textProperty().addListener((obs, oldVal, newVal) -> updateProgress());
        episodesTotalField.textProperty().addListener((obs, oldVal, newVal) -> updateProgress());
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
        //progressionbox visibilite :type serie
        typeCombo.valueProperty().addListener((obs, oldVal, newVal) -> {

            boolean isSerie = "SERIE".equals(newVal);

            progressionBox.setVisible(isSerie);
            progressionBox.setManaged(isSerie);
            if(!isSerie){
                serieProgressBar.setProgress(0);
                progressLabel.setText("0%");
            }
        });
        //progression Serie
        colProgress.setCellValueFactory(c -> {

            Contenu contenu = c.getValue();

            // 🔵 Priorité au statut
            if ("VU".equals(contenu.getStatut())) {
                return new javafx.beans.property.SimpleDoubleProperty(1.0).asObject();
            }

            if ("A_VOIR".equals(contenu.getStatut())) {
                return new javafx.beans.property.SimpleDoubleProperty(0.0).asObject();
            }

            // 🟡 Si EN_COURS
            if ("EN_COURS".equals(contenu.getStatut())) {

                // Si film → progression fixe (ex: 50%)
                if (!"SERIE".equals(contenu.getType())) {
                    return new javafx.beans.property.SimpleDoubleProperty(0.5).asObject();
                }

                // Si série → vraie progression
                ProgressionSerie p = apiService.getProgression(contenu.getId());

                if (p == null || p.getEpisodesTotaux() == 0) {
                    return new javafx.beans.property.SimpleDoubleProperty(0.0).asObject();
                }

                double progress =
                        (double) p.getEpisodesVus() / p.getEpisodesTotaux();

                return new javafx.beans.property.SimpleDoubleProperty(progress).asObject();
            }

            return new javafx.beans.property.SimpleDoubleProperty(0.0).asObject();
        });
        colProgress.setCellFactory(column -> new TableCell<>() {

            private final ProgressBar progressBar = new ProgressBar();
            private final Label percentLabel = new Label();
            private final VBox box = new VBox(progressBar, percentLabel);

            {
                progressBar.setPrefWidth(120);
                box.setSpacing(2);
            }

            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);

                if (empty || value == null) {
                    setGraphic(null);
                    return;
                }

                // Toujours afficher même si 0%
                progressBar.setProgress(value);

                percentLabel.setText(
                        String.format("%.0f%%", value * 100)
                );

                // 🎨 Couleur dynamique
                if (value < 0.3) {
                    progressBar.setStyle("-fx-accent: #ef4444;"); // rouge
                }
                else if (value < 0.7) {
                    progressBar.setStyle("-fx-accent: #f59e0b;"); // orange
                }
                else {
                    progressBar.setStyle("-fx-accent: #22c55e;"); // vert
                }

                setGraphic(box);
            }
        });

        tableContenus.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {

                    if (newSelection != null) {
                        remplirFormulaire(newSelection);
                    }
                });
        chargerContenus(); // toujours à la fin
    }


    public void chargerContenus(){
        tableContenus.setItems(
                FXCollections.observableArrayList(apiService.getAllContenus())
        );
        updateStats();
    }

    //Ajouter contenu

@FXML
public void ajouterContenu() {

    try {

        // Validation simple
        if (titreField.getText().isEmpty() ||
                anneeField.getText().isEmpty() ||
                genreCombo.getValue() == null) {

            showToast("⚠ Veuillez remplir tous les champs !");
            return;
        }

        Contenu c = new Contenu();
        c.setTitre(titreField.getText());
        c.setAnneeSortie(Integer.parseInt(anneeField.getText()));

        c.setType(typeCombo.getValue());
        c.setStatut(statutCombo.getValue());
        c.setWatchlist(watchlistCheck.isSelected());
        c.setNote(noteCombo.getValue());
        c.setGenreId(genreCombo.getValue().getId());

        Contenu saved = apiService.ajouterContenu(c);

        // Si série → sauvegarder progression
        if (saved != null && "SERIE".equals(saved.getType())) {

            if (!episodesVusField.getText().isEmpty() &&
                    !episodesTotalField.getText().isEmpty()) {

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
        }

        chargerContenus();

        // Animation
        ScaleTransition st =
                new ScaleTransition(Duration.millis(300), tableContenus);
        st.setFromX(0.95);
        st.setFromY(0.95);
        st.setToX(1);
        st.setToY(1);
        st.play();

        showToast("✔ Contenu ajouté avec succès !");

        clearForm();

    } catch (NumberFormatException e) {
        showToast("⚠ Année ou épisodes invalides !");
    }
}
    //Modifier contenu

    @FXML
    public void modifierContenu() {

        Contenu c = tableContenus.getSelectionModel().getSelectedItem();

        if (c == null) {
            showToast("⚠ Aucun élément sélectionné !");
            return;
        }

        try {

            c.setTitre(titreField.getText());
            c.setAnneeSortie(Integer.parseInt(anneeField.getText()));
            c.setType(typeCombo.getValue());
            c.setStatut(statutCombo.getValue());
            c.setWatchlist(watchlistCheck.isSelected());
            c.setNote(noteCombo.getValue());
            c.setGenreId(genreCombo.getValue().getId());

            // 🔥 1️⃣ Modifier contenu
            apiService.modifierContenu(c);

            // 🔥 2️⃣ ICI on met à jour la progression
            if ("SERIE".equals(c.getType())) {

                if (!episodesVusField.getText().isEmpty() &&
                        !episodesTotalField.getText().isEmpty()) {

                    int vus = Integer.parseInt(episodesVusField.getText());
                    int total = Integer.parseInt(episodesTotalField.getText());

                    ProgressionSerie existing =
                            apiService.getProgression(c.getId());

                    if (existing != null) {
                        existing.setEpisodesVus(vus);
                        existing.setEpisodesTotaux(total);
                        apiService.saveProgression(existing);//update plustard avec put en ApiService
                    } else {
                        ProgressionSerie p = new ProgressionSerie();
                        p.setContenuId(c.getId());
                        p.setEpisodesVus(vus);
                        p.setEpisodesTotaux(total);
                        apiService.saveProgression(p);
                    }
                }
            }

            // 🔥 3️⃣ Recharger table
            chargerContenus();
            tableContenus.refresh();

            clearForm();
            showToast("✔ Contenu modifié avec succès !");

        } catch (NumberFormatException e) {
            showToast("⚠ Données invalides !");
        }
    }
//    public void modifierContenu(){
//
//        Contenu c = tableContenus.getSelectionModel().getSelectedItem();
//        if(c == null) return;
//
//        c.setTitre(titreField.getText());
//        c.setAnneeSortie(Integer.parseInt(anneeField.getText()));
//
//        c.setType(typeCombo.getValue());
//        c.setStatut(statutCombo.getValue());
//        c.setWatchlist(watchlistCheck.isSelected());
//        c.setNote(noteCombo.getValue());
//        c.setGenreId(genreCombo.getValue().getId());
//
//        apiService.modifierContenu(c);
//        chargerContenus();
//    }
// Supprimer contenu

    @FXML
    public void supprimerContenu(){

        Contenu c = tableContenus.getSelectionModel().getSelectedItem();
        if(c == null) return;

        apiService.supprimerContenu(c.getId());
        chargerContenus();
    }

    //stats
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
    // toast
    private void showToast(String message) {

        toastLabel.setText(message);
        toastLabel.setVisible(true);
        toastLabel.setManaged(true);
        toastLabel.setOpacity(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), toastLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        PauseTransition stay = new PauseTransition(Duration.seconds(2));

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), toastLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        fadeOut.setOnFinished(e -> {
            toastLabel.setVisible(false);
            toastLabel.setManaged(false);
        });

        SequentialTransition sequence =
                new SequentialTransition(fadeIn, stay, fadeOut);

        sequence.play();
    }
    //initialiser le formulaire
    private void clearForm() {
        titreField.clear();
        anneeField.clear();
        episodesVusField.clear();
        episodesTotalField.clear();
        watchlistCheck.setSelected(false);
        typeCombo.setValue("FILM");
        statutCombo.setValue("A_VOIR");
        noteCombo.setValue(0);
    }
    private void remplirFormulaire(Contenu c) {

        titreField.setText(c.getTitre());
        anneeField.setText(String.valueOf(c.getAnneeSortie()));

        typeCombo.setValue(c.getType());
        statutCombo.setValue(c.getStatut());
        watchlistCheck.setSelected(c.isWatchlist());
        noteCombo.setValue(c.getNote());

        // Sélection genre
        genreCombo.getItems().stream()
                .filter(g -> g.getId() == c.getGenreId())
                .findFirst()
                .ifPresent(g -> genreCombo.setValue(g));

        // Si série → charger progression
        if ("SERIE".equals(c.getType())) {

            progressionBox.setVisible(true);
            progressionBox.setManaged(true);

            ProgressionSerie p = apiService.getProgression(c.getId());

            if (p != null) {
                episodesVusField.setText(String.valueOf(p.getEpisodesVus()));
                episodesTotalField.setText(String.valueOf(p.getEpisodesTotaux()));

                if (p.getEpisodesTotaux() > 0) {
                    double progress =
                            (double) p.getEpisodesVus() /
                                    p.getEpisodesTotaux();

                    serieProgressBar.setProgress(progress);
                    progressLabel.setText(
                            String.format("%.0f%%", progress * 100)
                    );
                }
            }

        } else {
            progressionBox.setVisible(false);
            progressionBox.setManaged(false);
        }
    }
}