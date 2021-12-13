package gui.controllers;

import be.Playlist;
import be.Song;
import gui.models.PlaylistModel;
import gui.models.SongModel;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MainMenuController {


    //* Her tager jeg dataen fra Fxml filen og sætter dem til at op tage data
    @FXML
    private TableView<Song> SongTable;
    @FXML
    private TableView<Playlist> PlaylistTable;


    @FXML
    private TableColumn<Song, String> TableTitle;
    @FXML
    private TableColumn<Song, String> TableArtist;
    @FXML
    private TableColumn<Song, String> TableCategory;
    @FXML
    private TableColumn<Song, String> TableTime;


    @FXML
    private TableColumn<Playlist, String> PlaylistName;
    @FXML
    private TableColumn<Playlist, Integer> playlistSongs;
    @FXML
    private TableColumn<Playlist, Double> playlistTime;

    ObservableList<Song> songData = FXCollections.observableArrayList();
    ObservableList<Song> searchData = FXCollections.observableArrayList();
    ObservableList<Playlist> playlistData = FXCollections.observableArrayList();
    MediaPlayer mediaPlayer;


    public Text songTextPlaying;
    private SongModel songModel;
    private PlaylistModel playlistModel;

    public Button songEditor;
    public Button songDeleter;
    public Button playButton;
    public Button closeButton;
    public Button NewPlaylist;
    public Button NewSong;
    public TextField filterBar;
    public Button filterSearch;
    public Button pauseButton;
    public Slider volumeSlider;
    public double volume = 0;
    private String SongPlaying;
    private Object ObservableList;


    public MainMenuController(){
        volumeSlider = new Slider();

    }


    public void mediaPlay() {
        if (mediaPlayer == null) {
            Media pick = new Media(new File(SongTable.getSelectionModel().getSelectedItem().getURL()).toURI().toString());
            mediaPlayer = new MediaPlayer(pick);
            mediaPlayer.play();
            songTextPlaying.setText(SongTable.getSelectionModel().getSelectedItem().getTitle());
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                mediaPlayer = null;
            });
        }else if (playButton.isManaged()){
            mediaPlayer.pause();
            mediaPlayer = null;
        }
    }

    public void goEditSong(ActionEvent actionEvent) throws IOException{
        Song selectedItem = SongTable.getSelectionModel().getSelectedItem();
        Stage swich = (Stage) songEditor.getScene().getWindow();
        FXMLLoader parent = new FXMLLoader(getClass().getResource("../view/EditSong.fxml"));
        Scene mainWindowScene = null;
        try {
            mainWindowScene = new Scene(parent.load());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage editSongStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editSongStage.setScene(mainWindowScene);

        EditSongController editSongController = parent.getController();
        editSongController.setSong(selectedItem);
        editSongStage.show();

    }


    public void GoNewPlaylist(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) NewPlaylist.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/NewPlaylist.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    public void GoNewSong(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) NewSong.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/NewSong.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    public void closeButton() {
        System.exit(0);
    }

    public void deleteSong(){
        songModel.deleteSong(SongTable.getSelectionModel().getSelectedItem());
        SongTable.getItems().remove(SongTable.getSelectionModel().getSelectedItem());
    }




    //*  her starter jeg programmet og sætter værdierne til at fortæller der er en song som er en String der hedder title et sted i koden. Den finder den og gør det
    //* samme for de andre
    public void initialize() throws Exception {
//
        songModel = new SongModel();

        TableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableTime.setCellValueFactory(new PropertyValueFactory<>("time"));


        //* her sætter jeg dataen til at blive vist i tabellen
        try {
            songData = FXCollections.observableList((songModel.getSongData()));
            TableViewLoad(songData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {mediaPlayer.setVolume(volume);
            }
        });

        playlistModel = new PlaylistModel();

        PlaylistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistSongs.setCellValueFactory(new PropertyValueFactory<>("songs"));
        playlistTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            playlistData = FXCollections.observableList(playlistModel.getPlaylistData());
            TableViewLoadet(playlistData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });

    }

    //TableView bliver generert

    private void TableViewLoad(ObservableList<Song> songData) {
        SongTable.setItems(getSongData());
    }

    private void TableViewLoader(ObservableList<Song> searchData) {
        SongTable.setItems(getSearchData());
    }

    private void TableViewLoadet(ObservableList<Playlist> playlistData) {
        PlaylistTable.setItems(getPlaylistData());
    }



    public ObservableList<Song> getSearchData() {
        return searchData;
    }

    //* her sætter jeg hvad song data er
    public ObservableList<Song> getSongData() {
        return songData;
    }

    public ObservableList<Playlist> getPlaylistData() {
        return playlistData;
    }

    public void filterSongs() throws Exception {
        try {
            searchData = FXCollections.observableList(songModel.searchSongs(filterBar.getText()));
            TableViewLoader(searchData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
