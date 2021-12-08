package gui.controllers;

import be.Playlist;
import be.Song;
import gui.models.PlaylistModel;
import gui.models.SongModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    //* Her tager jeg dataen fra Fxml filen og sætter dem til at op tage data
    @FXML
    private TableView<Song> SongTable;
    @FXML
    private TableView<Playlist> PlaylistTable;

    //    @FXML
//    private TableColumn<Song, String> clmID;
    @FXML
    private TableColumn<Song, String> TableTitle;
    @FXML
    private TableColumn<Song, String> TableArtist;
    @FXML
    private TableColumn<Song, String> TableCategory;
    @FXML
    private TableColumn<Song, Double> TableTime;


    @FXML
    private TableColumn<Playlist, String> PlaylistName;
    @FXML
    private TableColumn<Playlist, Integer> playlistSongs;
    @FXML
    private TableColumn<Playlist, Double> playlistTime;

    ObservableList<Song> songData = FXCollections.observableArrayList();
    ObservableList<Song> searchData = FXCollections.observableArrayList();
    ObservableList<Playlist> playlistData = FXCollections.observableArrayList();

    public static Label PlaylistTextNowPlaying;

    private SongModel songModel;


    public Button closeButton;
    public Button NewPlaylist;
    public Button NewSong;

    public TextField filterBar;
    public Button filterSearch;


//    public String TableTitle;
//    public String TableArtist;
//    public String TableCategory;
//    public double TableTime;


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

        PlaylistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistSongs.setCellValueFactory(new PropertyValueFactory<>("songs"));
        playlistTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        PlaylistModel playlistModel = new PlaylistModel();

        try {
            playlistData = FXCollections.observableList(playlistModel.getPlaylistData());
            TableViewLoadet(playlistData);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
