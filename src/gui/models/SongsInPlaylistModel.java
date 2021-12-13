package gui.models;

import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import bll.SongsInPlaylistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class SongsInPlaylistModel {
    private ObservableList<Song> songsInPlaylistToBeViewed;

    private SongsInPlaylistManager songsInPlaylistManager;


    public SongsInPlaylistModel(int PlaylistID) throws SQLException {
        songsInPlaylistManager = new SongsInPlaylistManager();
        songsInPlaylistToBeViewed = FXCollections.observableArrayList();
        songsInPlaylistToBeViewed.addAll(songsInPlaylistManager.getAllSongsInPlaylist(PlaylistID));
    }

    public ObservableList<Song> getSongsInPlaylistData(){
        return songsInPlaylistToBeViewed;
    }

    public void addSongToPlaylist(int PlaylistId, int SongId) {
        Song newSong = songsInPlaylistManager.addSongToPlaylist(PlaylistId, SongId);
        songsInPlaylistToBeViewed.add(newSong);
    }
}
