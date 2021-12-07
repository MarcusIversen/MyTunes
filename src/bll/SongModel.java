package bll;

import be.Song;
import bll.SongManager;
import gui.MainMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SongModel extends MainMenuController {
    private ObservableList<Song> songsToBeViewed;

    private SongManager songManager;

    public SongModel() throws Exception {
        songManager = new SongManager();
        songsToBeViewed = FXCollections.observableArrayList();
        songsToBeViewed.addAll(songManager.getAllSongs());
    }

public ObservableList<Song> getSongData(){
        return songsToBeViewed;
}

public void searchSong(String query) throws Exception{
    List<Song> searchResults = songManager.searchSongs(query);
    songsToBeViewed.clear();
    songsToBeViewed.addAll(searchResults);
}

}
