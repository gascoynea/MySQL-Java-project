package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 *Main application that calls the DB and opens up Login FXML form
 */
public class Main extends Application {
    /**
     * Opens Login Form FXML
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login Form.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Launches program and connects to the DB
     * @param args
     */
    public static void main(String[] args) {
        Database.DBConnection.openConnection();
        launch();
        Database.DBConnection.closeConnection();
    }
}