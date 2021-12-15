package gui.controllers;

import be.Playlist;
import be.Song;
import gui.models.PlaylistModel;
import gui.models.SongModel;
import gui.models.SongsInPlaylistModel;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainMenuController {


    //* Her tager jeg dataen fra Fxml filen og sætter dem til at op tage data
    @FXML
    private TableView<Song> SongTable;
    @FXML
    private TableView<Playlist> PlaylistTable;
    @FXML
    public ListView songsInPlaylistTable;


    @FXML
    public TableColumn<Song, String> TableSongsId;
    @FXML
    public TableColumn<Song, String> TableSongs;
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
    ObservableList<Song> PLSongsData = FXCollections.observableArrayList();
    MediaPlayer mediaPlayer;


    public Text songTextPlaying;
    public Label CurrentTime;
    public Label MaxTime;
    private SongModel songModel;
    private PlaylistModel playlistModel;
    private SongsInPlaylistModel songsInPlaylistModel;
    private Playlist selectedPlaylist;

    public Button refreshButton;
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
    public Slider timeSlider;
    public double volume = 0;
    private Object ObservableList;
    private List<Song> SongsPlayed;
    private int IndexOfSongPlaying;
    Song songToPlayIfSet = null; //Hvis sat afspilles denne når man kalder mediaPlay, bliver sat til null efter afspilningen er startet

    private Boolean IsPaused = false;
    //Brugt til decimal formatering til 2 decimaler
    private static final DecimalFormat dfSharp = new DecimalFormat("#:##");


    public MainMenuController() throws SQLException {

        songModel = new SongModel();
        volumeSlider = new Slider();
        PlaylistTable = new TableView<>();
        songsInPlaylistModel = new SongsInPlaylistModel();
        playlistModel = new PlaylistModel();
    }

    //*  her starter jeg programmet og sætter værdierne til at fortæller der er en song som er en String der hedder title et sted i koden. Den finder den og gør det
    //* samme for de andre
    public void initialize() throws Exception {
//
        SongsPlayed = new ArrayList<Song>();

        TableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableTime.setCellValueFactory(new PropertyValueFactory<>("time"));


        //* her sætter jeg dataen til at blive vist i tabellen
        try {
            songData = FXCollections.observableList((songModel.getSongData()));
            songTableViewLoad(songData);
        } catch (Exception e) {
            e.printStackTrace();
        }

        volumeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(volume);
            }
        });

        PlaylistName.setCellValueFactory(new PropertyValueFactory<>("name"));
        playlistSongs.setCellValueFactory(new PropertyValueFactory<>("SongCount"));
        playlistTime.setCellValueFactory(new PropertyValueFactory<>("time"));

        try {
            playlistData = FXCollections.observableList(playlistModel.getPlaylistData());
            playlistTableViewLoad(playlistData);
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

    public void mediaPlay() {
        boolean shouldRun = true;
        if (mediaPlayer != null) {
            //Tjekker om der er en sang der allerede spiller. Hvis der er og ikke en ny er markeret sker der ingenting
            String status = mediaPlayer.getStatus().toString(); //Det er på en separat linje pga debug er lettere så
            shouldRun = !(mediaPlayer != null && status == "PLAYING"
                    && songToPlayIfSet == null && songsInPlaylistTable.getSelectionModel().getSelectedItem() == null);
        }
        if (shouldRun) {
            if (IsPaused) {
                IsPaused = false;
                mediaPlayer.play();
            } else {
                if (mediaPlayer != null) {
                    //Hvis der er en kørende mediaplayer, så stoppes denne inden en ny startes
                    mediaPlayer.dispose();
                }
                if (songToPlayIfSet == null) {
                    songToPlayIfSet = (Song) songsInPlaylistTable.getSelectionModel().getSelectedItem();
                    IndexOfSongPlaying = SongsPlayed.indexOf(songToPlayIfSet);
                }
                Media pick = new Media(new File(songToPlayIfSet.getURL()).toURI().toString());
                mediaPlayer = new MediaPlayer(pick);
                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);

                mediaPlayer.play();
                songTextPlaying.setText(songToPlayIfSet.getTitle());
                playButton.setVisible(false);
                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                    NextSongBtnClicked(new ActionEvent());
                });
                songToPlayIfSet = null;
            }
            mediaPlayer.currentTimeProperty().addListener((observableValue, oldDuration, newDuration) -> {
               System.out.println("Player:" + observableValue + " | Changed from playing at: " + oldDuration + " to play at " + newDuration);
                timeSlider.setValue((newDuration.toSeconds() / mediaPlayer.getTotalDuration().toSeconds()) * 100);
               /*  if(mediaPlayer != null) {
                    String totalTime = String.valueOf(mediaPlayer.getTotalDuration().toMillis() / 60000);
                    CurrentTime.setText(String.valueOf(newDuration.toSeconds() / 60));
                    MaxTime.setText(totalTime);
                } */
            });
        }

        System.out.println(mediaPlayer.getStatus());
    }

    public void mediaPause() {
        IsPaused = true;
        mediaPlayer.pause();
        System.out.println(mediaPlayer.getStatus());
    }


    public void addSongToPlaylist() {

        songsInPlaylistTable.refresh();

        Playlist PlaylistId = PlaylistTable.getSelectionModel().getSelectedItem();
        Song songId = SongTable.getSelectionModel().getSelectedItem();

        songsInPlaylistModel.addSongToPlaylist(PlaylistId.getPlaylistId(), songId.getId());

        reloadPlaylistTable();
        reloadSongsInPlaylistTable();

    }

    private void reloadPlaylistTable() {
        try {
            int index = PlaylistTable.getSelectionModel().getFocusedIndex();
            this.PlaylistTable.setItems(FXCollections.observableList(playlistModel.getPlaylist()));
            PlaylistTable.getSelectionModel().select(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadSongsInPlaylistTable() {

        songsInPlaylistTable.refresh();

        Playlist playlist = PlaylistTable.getSelectionModel().getSelectedItem();

        List<Song> observableList = (playlist.getSongs());
        songsInPlaylistTable.setItems(FXCollections.observableList(observableList));

    }


    public void goEditSong(ActionEvent actionEvent) throws IOException {
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

    public void deleteSong() {
        songModel.deleteSong(SongTable.getSelectionModel().getSelectedItem());
        SongTable.getItems().remove(SongTable.getSelectionModel().getSelectedItem());
    }


    public void lookAtPlaylist() {
        Playlist playlist = PlaylistTable.getSelectionModel().getSelectedItem();
        this.SongsPlayed = new ArrayList<Song>();

        List<Song> songs = (playlist.getSongs());
        for (Song song : songs) {
            this.SongsPlayed.add(song);
        }
 /*       if (songs.size() > 0) {
            IndexOfSongPlaying = 0;
            IsPaused = false;
            songToPlayIfSet = this.SongsPlayed.get(0);
            this.mediaPlay();
        } */
        songsInPlaylistTable.setItems(FXCollections.observableList(songs));

    }


    //TableView bliver generert

    private void songTableViewLoad(ObservableList<Song> songData) {
        SongTable.setItems(getSongData());
    }

    private void searchTableViewLoad(ObservableList<Song> searchData) {
        SongTable.setItems(getSearchData());
    }

    private void playlistTableViewLoad(ObservableList<Playlist> playlistData) {
        PlaylistTable.setItems(getPlaylistData());
    }

    private void PLSongsTableViewLoad(ObservableList<Song> PLSongsData) {
        songsInPlaylistTable.setItems(getPLSongsData());
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

    public ObservableList<Song> getPLSongsData() {
        return PLSongsData;
    }

    public void filterSongs() throws Exception {
        try {
            searchData = FXCollections.observableList(songModel.searchSongs(filterBar.getText()));
            searchTableViewLoad(searchData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void PreviousSongBtnClicked(ActionEvent event) {
        if (SongsPlayed.size() > 0) {
            //Find song on index - 1 if size is bigger than 1
            if (SongsPlayed.size() == 1) {
                IndexOfSongPlaying = 0;
                songToPlayIfSet = SongsPlayed.get(0);
            } else {
                //Hvis først afspillet sang, hopper vi til den sidste i listen
                IndexOfSongPlaying = IndexOfSongPlaying != 0 ? IndexOfSongPlaying - 1 : SongsPlayed.size() - 1;
                songToPlayIfSet = SongsPlayed.get(IndexOfSongPlaying);
            }
            mediaPlay();
        }
    }

    public void NextSongBtnClicked(ActionEvent event) {
        if (SongsPlayed.size() > 1) {
            IndexOfSongPlaying = IndexOfSongPlaying == SongsPlayed.size() - 1 ? 0 : IndexOfSongPlaying + 1;
            songToPlayIfSet = SongsPlayed.get(IndexOfSongPlaying);
            mediaPlay();
        }
    }

    public void timeSlideInSong(MouseEvent mouseEvent) {



    }
}
