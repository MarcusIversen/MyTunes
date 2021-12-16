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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private MediaPlayer mediaPlayer;

    public void initialize() {

        songModel = new SongModel();
        categoryMenu.setItems(FXCollections.observableArrayList("Pop", "Hip Hop", "Rap", "Rock", "Dance", "Techno", "Latin music", "Indie Rock", "Classical", "Country", "Metal", "RnB"));
    }

    public void GoReturnMainMenu(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) ReturnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);

    }


    public void uploadSongInfo(String title, String artist, String category, int time) throws IOException, SQLException {
        songModel.createSong(title, artist, category, time, fileText.getText());
    }

    public void getSongInfo() throws IOException, SQLException {
        String uploadTitle = title();
        String uploadArtist = artist();
        String uploadCategory = category();
        int uploadTime = (int) mediaPlayer.getMedia().getDuration().toSeconds();
        uploadSongInfo(uploadTitle, uploadArtist, uploadCategory, uploadTime);

        Stage swich = (Stage) ReturnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
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

    public String time() {
        String timeTemp = timeBar.getText();
        return timeTemp;
    }


    public void chooseFile() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File defaultDirectory = new File("src/dal/db/songFiles");
        fileChooser.setInitialDirectory(defaultDirectory);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp3 and wav files", "*.mp3", "*.wav"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            fileText.appendText("src/dal/db/songFiles/" + selectedFile.getName());
            Media pick = new Media(new File(selectedFile.getAbsolutePath()).toURI().toString());
            mediaPlayer = new MediaPlayer(pick);
            mediaPlayer.setOnReady(() -> {
                String timeInSeconds = String.format("%1.0f", mediaPlayer.getMedia().getDuration().toSeconds());
                int minuts = Integer.parseInt(timeInSeconds) / 60;
                int seconds = Integer.parseInt(timeInSeconds) % 60;
                if (10 > seconds) {
                    timeBar.setText(minuts + ":0" + seconds);
                } else {
                    timeBar.setText(minuts + ":" + seconds);
                }
            });
        } else System.out.println("File is not valid");
    }

    public void attachFile() throws IOException {

    }

}
