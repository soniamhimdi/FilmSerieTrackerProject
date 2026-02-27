module com.maisonneuve.filmserietrackerfx {

        requires javafx.controls;
        requires javafx.fxml;
        requires java.net.http;
        requires com.google.gson;
        requires javafx.base;

        opens com.maisonneuve.filmserietrackerfx to javafx.fxml;
        opens com.maisonneuve.filmserietrackerfx.controller to javafx.fxml;
        opens com.maisonneuve.filmserietrackerfx.model to javafx.base, com.google.gson;

        exports com.maisonneuve.filmserietrackerfx;
        exports com.maisonneuve.filmserietrackerfx.controller;
        }
