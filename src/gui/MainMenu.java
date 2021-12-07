package gui;

import be.Playlist;
import be.Song;
import bll.SongManager;
import bll.SongSearcher;
import dal.db.SongDAO_DB;
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
import java.sql.SQLException;

public class MainMenu {


    ObservableList<Song> songData = FXCollections.observableArrayList();
    ObservableList<Song> searchData = FXCollections.observableArrayList();
    ObservableList<Playlist> playlistData = FXCollections.observableArrayList();
    ObservableList<Playlist> playlistSerchData = FXCollections.observableArrayList();

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


    public static Label PlaylistTextNowPlaying;

    private SongSearcher songSearcher = new SongSearcher();
    private SongManager songManager = new SongManager();

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
        Parent parent = FXMLLoader.load(getClass().getResource("NewPlaylist.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    public void GoNewSong(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) NewSong.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("NewSong.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }
    
    public void closeButton() {
        System.exit(0);
    }


    //*  her starter jeg programmet og sætter værdierne til at fortæller der er en song som er en String der hedder title et sted i koden. Den finder den og gør det
    //* samme for de andre
    public void initialize() {
//        clmID.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        TableTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        TableArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
        TableCategory.setCellValueFactory(new PropertyValueFactory<Song, String>("category"));
        TableTime.setCellValueFactory(new PropertyValueFactory<Song, Double>("time"));

        PlaylistName.setCellValueFactory(new PropertyValueFactory<Playlist, String>("title"));
        playlistSongs.setCellValueFactory(new PropertyValueFactory<Playlist, Integer>("songs"));
        playlistTime.setCellValueFactory(new PropertyValueFactory<Playlist, Double>("playTime"));
        SongDAO_DB songDbLogic = new SongDAO_DB();
        playlistDB playlistDbLogic = new SongDAO_DB();

        //* her sætter jeg dataen til at blive vist i tabellen
        try {
            songData = FXCollections.observableList(songDbLogic.getAllSongs());
            TableViewLoad(songData);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
           playlistData = FXCollections.observableList(playlistDbLogic.getAllPlaylists());
            TableViewLoad(playlistData);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //TableView bliver generert
    private void TableViewLoad(ObservableList<Song> songData) {
        SongTable.setItems(getSongData());
        PlaylistTable.setItems(getPlaylistData());
    }

    private void TableViewLoader(ObservableList<Song> searchData) {
        SongTable.setItems(getSearchData());
        PlaylistTable.setItems(getplaylistSerchData());
    }


    public ObservableList<Song> getSearchData() {
        return searchData;
    }

    public ObservableList<Playlist> getplaylistSerchData() {
        return playlistSerchData;
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
            searchData = FXCollections.observableList(songManager.searchSongs(filterBar.getText()));
            TableViewLoader(searchData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
