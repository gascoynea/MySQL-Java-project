package com.example.demo;

import BDAccess.DBACountries;
import Model.Countries;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Label welcomeText;
    public TableColumn idCol;
    public TableColumn nameCol;
    public TableView dataTable;

    public void initialize(URL url, ResourceBundle resourceBundle){

    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        ObservableList<Countries> countryList = DBACountries.getAllCountries();
        for(Countries C : countryList){
            System.out.println("CID: " + C.getId() + " CName: " + C.getName());
        }

    }
}