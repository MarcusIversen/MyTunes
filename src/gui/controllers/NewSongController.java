package gui.controllers;

import bll.SongManager;
import gui.models.SongModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.sql.SQLException;


public class NewSongController {

    public Button ReturnMainMenu;
    public TextField titleBar;
    public TextField artistBar;
    public TextField timeBar;
    public ComboBox<String> categoryMenu;
    public Button songSaveButton;
    public TextField fileText;

    SongModel songModel;

    public void initialize() {

        songModel = new SongModel();

        categoryMenu.setItems(FXCollections.observableArrayList("Pop", "Rock", "Reggae", "Techno", "RnB"));
    }

    public void GoReturnMainMenu(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) ReturnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);

    }


    public void uploadSongInfo(String title, String artist, String category, double time) throws IOException, SQLException {
        songModel.createSong(title, artist, category, time, fileText.getText());
    }

    public void getSongInfo() throws IOException, SQLException {
        String uploadTitle = title();
        String uploadArtist = artist();
        String uploadCategory = category();
        double uploadTime = time();
        uploadSongInfo(uploadTitle, uploadArtist, uploadCategory, uploadTime);


    }


    public String title() {
        String titleTemp = titleBar.getText();
        return titleTemp;

    }

    public String artist() {
        String artistTemp = artistBar.getText();
        return artistTemp;
    }

    public String category() {
        String categoryTemp = categoryMenu.getSelectionModel().getSelectedItem().toString();
        return categoryTemp;
    }

    public double time() {
        double timeTemp = timeBar.getPrefColumnCount();
        return timeTemp;
    }


    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        File defaultDirectory = new File("src/dal/db/songFiles");
        fileChooser.setInitialDirectory(defaultDirectory);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp3 and wav files", "*.mp3", "*.Wav"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            fileText.appendText(selectedFile.getAbsolutePath());
        } else System.out.println("File is not valid");
    }

    public void attachFile() throws IOException {

    }

}
