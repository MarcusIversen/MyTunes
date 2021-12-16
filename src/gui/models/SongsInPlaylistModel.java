package gui.models;

import be.Song;
import bll.SongsInPlaylistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class SongsInPlaylistModel {
    private ObservableList<Song> songsInPlaylistToBeViewed;

    private SongsInPlaylistManager songsInPlaylistManager;


    /**
     * metoder der forbinder vores CRUD til vores controller, et led i vores trelagsarkitektur
     * @throws SQLException
     */
    public SongsInPlaylistModel() throws SQLException {
        songsInPlaylistManager = new SongsInPlaylistManager();
        songsInPlaylistToBeViewed = FXCollections.observableArrayList();
    }


    public void addSongToPlaylist(int PlaylistId, int SongId) {
        Song newSong = songsInPlaylistManager.addSongToPlaylist(PlaylistId, SongId);
        songsInPlaylistToBeViewed.add(newSong);
    }

    public void deleteSongInPlaylist(int PlaylistId, int SongId) {
        songsInPlaylistManager.deleteSongInPlaylist(PlaylistId, SongId);
    }
}
