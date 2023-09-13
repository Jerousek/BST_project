package com.example.javafxtest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author 42072
 */
public class LibraryApplication extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibraryApplication.class.getResource("LibraryGui.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Knihy");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
