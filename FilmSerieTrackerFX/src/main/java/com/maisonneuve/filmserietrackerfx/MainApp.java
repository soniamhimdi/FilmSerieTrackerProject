package com.maisonneuve.filmserietrackerfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/view/contenu-view.fxml")
        );

        Scene scene = new Scene(loader.load());

        // CSS
        scene.getStylesheets().add(
                getClass().getResource("/style/app.css").toExternalForm()
        );

        stage.setTitle("Film & Serie Tracker");
        stage.setScene(scene);
        stage.setWidth(1000);
        stage.setHeight(650);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}