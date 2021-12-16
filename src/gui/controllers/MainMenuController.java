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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
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
    MediaPlayer mediaPlayer;


    public Text songTextPlaying;
    public Label CurrentTime;
    public Label MaxTime;
    private SongModel songModel;
    private PlaylistModel playlistModel;
    private SongsInPlaylistModel songsInPlaylistModel;


    public Button playlistDeleteBtn;
    public Button songsInPlaylistDeleter;
    public Button playlistDeleter;
    public Button editPLaylistbutton;
    public Button songEditor;
    public Button songDeleter;
    public Button playButton;
    public Button closeButton;
    public Button NewPlaylist;
    public Button NewSong;
    public TextField filterBar;
    public Button filterSearch;
    public Slider volumeSlider;
    public Slider timeSlider;
    public double volume = 0;
    private List<Song> SongsPlayed;
    private int IndexOfSongPlaying;
    Song songToPlayIfSet = null; //Hvis sat afspilles denne når man kalder mediaPlay, bliver sat til null efter afspilningen er startet

    private Boolean IsPaused = false;
    //Brugt til decimal formatering til 2 decimaler
    private static final DecimalFormat dfSharp = new DecimalFormat("#:##");

    /**
     * Constructoren der instancierer vores variabler.
     * @throws SQLException
     */
    public MainMenuController() throws SQLException {

        songModel = new SongModel();
        volumeSlider = new Slider();
        PlaylistTable = new TableView<>();
        songsInPlaylistModel = new SongsInPlaylistModel();
        playlistModel = new PlaylistModel();
    }

    /**
     * Initialize metode, som hovedsageligt viser alt vores data i vores tabels.
     * Udover det bruges vores volumeslider også i denne metode.
     * @throws Exception
     */
    public void initialize() throws Exception {

        SongsPlayed = new ArrayList<Song>();

        TableTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        TableArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        TableCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        TableTime.setCellValueFactory(new PropertyValueFactory<>("duration"));


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
        playlistTime.setCellValueFactory(new PropertyValueFactory<>("duration"));

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

    /**
     * mediaPlay metode, der er brugt til at afspille vores sange, samt pause dem.
     * Der tjekkes for om en sang er igang med at spille eller pauset.
     */

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
                if (mediaPlayer != null) {
                    timeSlider.setValue((newDuration.toSeconds() / mediaPlayer.getTotalDuration().toSeconds()) * 100);


                    //System.out.println("Player:" + observableValue + " | Changed from playing at: " + oldDuration + " to play at " + newDuration);

                    timeSlider.setValue((newDuration.toSeconds() / mediaPlayer.getTotalDuration().toSeconds()) * 100);
                    //if(mediaPlayer != null) {
                    //String totalTime = String.valueOf(mediaPlayer.getTotalDuration().toMillis() / 60000);
                    // CurrentTime.setText(String.valueOf(newDuration.toSeconds() / 60));
                    // MaxTime.setText(totalTime);
                }
            });
        }


    }

    /**
     * mediaPause, pause metoden til at stoppe vores musik.
     */

    public void mediaPause() {
        IsPaused = true;
        mediaPlayer.pause();
    }


    /**
     * Endelige metod til at tilføje en sang til en playliste, når der trykkes på knappen, bliver en sang tilføjet til
     * en playliste.
     */
    public void addSongToPlaylist() {

        songsInPlaylistTable.refresh();

        Playlist PlaylistId = PlaylistTable.getSelectionModel().getSelectedItem();
        Song songId = SongTable.getSelectionModel().getSelectedItem();

        songsInPlaylistModel.addSongToPlaylist(PlaylistId.getPlaylistId(), songId.getId());

        reloadPlaylistTable();
        reloadSongsInPlaylistTable();

    }

    /**
     * Metode der bliver taget i brug når en sang bliver valgt og derefter trykkes delete.
     */
    public void deleteSongInPlaylist() {

        songsInPlaylistTable.refresh();

        Playlist PlaylistId = PlaylistTable.getSelectionModel().getSelectedItem();
        Song songId = (Song) songsInPlaylistTable.getSelectionModel().getSelectedItem();

        songsInPlaylistModel.deleteSongInPlaylist(PlaylistId.getPlaylistId(), songId.getId());

        reloadPlaylistTable();
        reloadSongsInPlaylistTable();
    }

    /**
     * Her reloader vi vores playlistTable, når en ny playlist bliver tilføjet eller edited.
     */
    private void reloadPlaylistTable() {
        try {
            int index = PlaylistTable.getSelectionModel().getFocusedIndex();
            this.PlaylistTable.setItems(FXCollections.observableList(playlistModel.getPlaylist()));
            PlaylistTable.getSelectionModel().select(index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Her reloader vi vores listView med sangene på en specifik playliste.
     * dette sker når en playlist bliver valgt, en sang bliver tilføjet eller slettet osv.
      */
    public void reloadSongsInPlaylistTable() {

        songsInPlaylistTable.refresh();

        Playlist playlist = PlaylistTable.getSelectionModel().getSelectedItem();

        List<Song> observableList = (playlist.getSongs());
        songsInPlaylistTable.setItems(FXCollections.observableList(observableList));
    }

    /**
     * Metode der tages i brug, når knappen edit bliver trykket på.
     * Sangen bliver edited, med det input du selv kommer med.
     * Efter der bliver trykket bliver stagen ændret tilbage til main menu.
     * @param actionEvent
     * @throws IOException
     */

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
        mediaPlayer.stop();
    }

    /**
     * Metode der tages i brug, når knappen edit bliver trykket på.
     * Playlisten bliver edited, med det input du selv kommer med.
     * Efter der bliver trykket bliver stagen ændret tilbage til main menu.
     * @param actionEvent
     * @throws IOException
     */


    public void goEditPlaylist(ActionEvent actionEvent) throws IOException {

        Playlist selectedItem = PlaylistTable.getSelectionModel().getSelectedItem();

        Stage swich = (Stage) NewPlaylist.getScene().getWindow();
        FXMLLoader parent = new FXMLLoader(getClass().getResource("../view/EditPlaylist.fxml"));
        Scene mainWindowScene = null;
        try {
            mainWindowScene = new Scene(parent.load());
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage editPlaylistStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editPlaylistStage.setScene(mainWindowScene);

        EditPlaylistController editPlaylistController = parent.getController();
        editPlaylistController.setPlaylist(selectedItem);
        editPlaylistStage.show();
        mediaPlayer.stop();
    }


    /**
     * metode der bliver taget i brug, når man vil tilføje en ny playliste.
     * Trykkes der, åbnes et ny UI, hvor du kan lave en ny playliste.
     * @param actionEvent
     * @throws IOException
     */
    public void GoNewPlaylist(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) NewPlaylist.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/NewPlaylist.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
        mediaPlayer.stop();
    }

    /**
     * Metode der bliver taget i brug, når man vil tilføje en ny sang.
     * Trykkes der, åbnes et nyt UI, hvor du kan tilføje en ny sang.
     * @param actionEvent
     * @throws IOException
     */
    public void GoNewSong(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) NewSong.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/NewSong.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
        mediaPlayer.stop();
    }

    /**
     * Knap der lukker vores app.
     */
    public void closeButton() {
        System.exit(0);
    }

    /**
     * Metode der tages i brug når man først vælger en sang og så trykker delete.
     * Deletes der, fjernes sangen både fra vores table, men også fra vores database.
     */

    public void deleteSong() {
        songModel.deleteSong(SongTable.getSelectionModel().getSelectedItem());
        SongTable.getItems().remove(SongTable.getSelectionModel().getSelectedItem());
    }

    /**
     * Metode der tages i brug når man først vælger en playlist og så trykker delete.
     *  Deletes der, fjernes playlisten fra table og database, men kun hvis den er tom.
     */

    public void deletePlaylist() {
        playlistModel.deletePlaylist(PlaylistTable.getSelectionModel().getSelectedItem());
        PlaylistTable.getItems().remove(PlaylistTable.getSelectionModel().getSelectedItem());
    }

    /**
     * Metoden her bruges når man trykker på en playlist.
     * Trykkes der, bliver playlistens sange vist i vores listview ved navn, SongsInPlaylistTable
     */
    public void lookAtPlaylist() {
        Playlist playlist = PlaylistTable.getSelectionModel().getSelectedItem();
        this.SongsPlayed = new ArrayList<Song>();

        List<Song> songs = (playlist.getSongs());
        for (Song song : songs) {
            this.SongsPlayed.add(song);
        }

        songsInPlaylistTable.setItems(FXCollections.observableList(songs));

        

    }


    /**
     * Tableview loaders, der er med til at vise vores data i vores tableviews.
     * @param songData
     */

    private void songTableViewLoad(ObservableList<Song> songData) {
        SongTable.setItems(getSongData());
    }

    private void searchTableViewLoad(ObservableList<Song> searchData) {
        SongTable.setItems(getSearchData());
    }

    private void playlistTableViewLoad(ObservableList<Playlist> playlistData) {
        PlaylistTable.setItems(getPlaylistData());
    }

    /**
     * Dataen som skal vises i vores tables, bliver hentet ned her.
     * @return
     */

    public ObservableList<Song> getSearchData() {
        return searchData;
    }


    public ObservableList<Song> getSongData() {
        return songData;
    }

    public ObservableList<Playlist> getPlaylistData() {
        return playlistData;
    }

    /**
     * Metode der bruges til at loade den data som vi får gennem vores søgefunktion.
     * Søges der på en sangtitle, loades table'n for ny, med det der er søgt på.
     */
    public void filterSongs() {
        try {
            searchData = FXCollections.observableList(songModel.searchSongs(filterBar.getText()));
            searchTableViewLoad(searchData);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Metode der bruges når knappen, sang tilbage bliver trykket på.
     * Når der trykkes, henter den sangen tidligere i vores arrayliste og så afspilles den.
     * @param event
     */

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

    /**
     * Metode der bruges når man trykker næste sang.
     * Trykkes der, bliver den næste sang hentet fra vores arrayliste og så afspillet.
     * @param event
     */

    public void NextSongBtnClicked(ActionEvent event) {
        if (SongsPlayed.size() > 1) {
            IndexOfSongPlaying = IndexOfSongPlaying == SongsPlayed.size() - 1 ? 0 : IndexOfSongPlaying + 1;
            songToPlayIfSet = SongsPlayed.get(IndexOfSongPlaying);
            mediaPlay();
        }
    }

    /**
     * Knappen med pilen op, gør at man nu kan rykke en sang op i playlisten.
     */

    public void moveSongUp() {
        if(songsInPlaylistTable.getSelectionModel().getSelectedItem() != null) // check if the user really selected a row in the table
        {
            if(songsInPlaylistTable.getSelectionModel().getSelectedIndex() != 0) // if the row first one so do nothing
            {
                int index = songsInPlaylistTable.getSelectionModel().getSelectedIndex(); // get the selected row index
                Song x = (Song) songsInPlaylistTable.getSelectionModel().getSelectedItem(); // get the selected item
                songsInPlaylistTable.getItems().set(index, songsInPlaylistTable.getItems().get(index-1)); // move the selected item up
                songsInPlaylistTable.getItems().set(index-1, x); // change the row with the item in above
                songsInPlaylistTable.getSelectionModel().select(index-1); // select the new row position
            }
        }
    }

    /**
     * Knappen med pilen ned, gør at man kan rykke en sang ned i playlisten
     */
    public void moveSongDown() {
        {
            if(songsInPlaylistTable.getSelectionModel().getSelectedItem() != null)
            {
                if(songsInPlaylistTable.getSelectionModel().getSelectedIndex() != songsInPlaylistTable.getItems().size()-1) // if the row is in last so dont do nothing
                {
                    int index = songsInPlaylistTable.getSelectionModel().getSelectedIndex();
                    Song x = (Song) songsInPlaylistTable.getSelectionModel().getSelectedItem();
                    songsInPlaylistTable.getItems().set(index, songsInPlaylistTable.getItems().get(index+1));
                    songsInPlaylistTable.getItems().set(index+1, x);
                    songsInPlaylistTable.getSelectionModel().select(index+1);
                }
            }
        }
    }
}
