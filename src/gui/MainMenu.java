package gui;

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

    //* Her tager jeg dataen fra Fxml filen og sætter dem til at op tage data
    @FXML
    private TableView<Song> SongTable;
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


    public static Label PlaylistTextNowPlaying;

    private SongSearcher songSearcher = new SongSearcher();
    private SongManager songManager = new SongManager();


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

    //*  her starter jeg programmet og sætter værdierne til at fortæller der er en song som er en String der hedder title et sted i koden. Den finder den og gør det
    //* samme for de andre
    public void initialize() {
//        clmID.setCellValueFactory(new PropertyValueFactory<>("ticketId"));
        TableTitle.setCellValueFactory(new PropertyValueFactory<Song, String>("title"));
        TableArtist.setCellValueFactory(new PropertyValueFactory<Song, String>("artist"));
        TableCategory.setCellValueFactory(new PropertyValueFactory<Song, String>("category"));
        TableTime.setCellValueFactory(new PropertyValueFactory<Song, Double>("time"));
        SongDAO_DB songDbLogic = new SongDAO_DB();

        //* her sætter jeg dataen til at blive vist i tabellen
        try {
            songData = FXCollections.observableList(songDbLogic.getAllSongs());
            TableViewLoad(songData);
        } catch (SQLException e) {
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


    public ObservableList<Song> getSearchData() {
        return searchData;
    }

 //* her sætter jeg hvad song data er 
    public ObservableList<Song> getSongData() {
        return songData;
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
