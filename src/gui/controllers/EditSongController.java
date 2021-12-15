package gui.controllers;

import be.Playlist;
import be.Song;
import com.sun.tools.javac.Main;
import gui.models.SongModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import jdk.javadoc.internal.doclets.formats.html.markup.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static jdk.javadoc.internal.doclets.formats.html.markup.HtmlStyle.title;

public class EditSongController implements Initializable {

    MainMenuController mainMenuController = new MainMenuController();


    SongModel songModel = new SongModel();
    public TextField titleBar;
    public TextField artistBar;
    public TextField timeBar;
    public TextField fileText;
    public Button ReturnMainMenu;
    public Button updateSongButton;
    public ComboBox categoryMenu;
    public TextField idBar;
    private MediaPlayer mediaPlayer;
    private int timeToSave = -1;

    public EditSongController() throws SQLException {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        categoryMenu.setItems(FXCollections.observableArrayList("Pop", "Rock", "Reggae", "Techno", "RnB"));

    }


    public void GoReturnMainMenu(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) ReturnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    public void updateSong(ActionEvent actionEvent) throws IOException {

        int updateId = Integer.parseInt(idBar.getText());
        String updateTitle = titleBar.getText();
        String updateArtist = artistBar.getText();
        String updateCategory = categoryMenu.getSelectionModel().getSelectedItem().toString();
        int updateTime = timeToSave;
        String updateUrl = fileText.getText();


        Song song = new Song(updateId, updateTitle, updateArtist, updateCategory, updateTime, updateUrl);
        songModel.updateSong(song);

        GoReturnMainMenu(actionEvent);
    }

    public void setSong(Song song) {
        titleBar.setText(song.getTitle());
        artistBar.setText(song.getArtist());
        categoryMenu.getSelectionModel().select(song.getCategory());
        timeBar.setText(song.getDuration());
        idBar.setText(Integer.toString(song.getId()));
        fileText.setText(song.getURL());
        timeToSave = song.getTime();
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
                timeToSave = (int) mediaPlayer.getMedia().getDuration().toSeconds();
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


}
