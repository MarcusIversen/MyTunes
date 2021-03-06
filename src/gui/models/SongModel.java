package gui.models;

import be.Song;
import bll.ISongManager;
import bll.SongManager;
import gui.controllers.MainMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class SongModel {
    private ObservableList<Song> songsToBeViewed;

    private ISongManager songManager;


    /**
     * Metoder der er med til at forbinde vores CRUD til controlleren, hvilket er et led i vores trelags arkitektur
     */
    public SongModel() {
        songManager = new SongManager();
        songsToBeViewed = FXCollections.observableArrayList();
        songsToBeViewed.addAll(songManager.getAllSongs());
    }

    public ObservableList<Song> getSongData() {
        return songsToBeViewed;
    }


    /**
     * Songsearcher metode, der return et searchresult.
     * @param text
     * @return
     */
    public List<Song> searchSongs(String text) {
        List<Song> searchResults = null;

        searchResults = songManager.searchSongs(text);
        songsToBeViewed.clear();
        songsToBeViewed.addAll(searchResults);

        return searchResults;
    }

    public void createSong(String title, String artist, String category, int time, String URL) {
        Song newSong = songManager.createSong(title, artist, category, time, URL);
        songsToBeViewed.add(newSong);
    }

    public void updateSong(Song song) {
        songManager.updateSong(song);
    }

    public void deleteSong(Song song) {
        songManager.deleteSong(song);
    }
}
