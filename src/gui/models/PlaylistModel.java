package gui.models;

import be.Playlist;
import bll.PlaylistManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public class PlaylistModel {
    private ObservableList<Playlist> playlistToBeViewed;

    private PlaylistManager playlistManager;

    public PlaylistModel() throws SQLException {
        playlistManager = new PlaylistManager();
        playlistToBeViewed = FXCollections.observableArrayList();
        playlistToBeViewed.addAll(playlistManager.getAllPlaylists());
    }

    public ObservableList<Playlist> getPlaylistData(){
        return playlistToBeViewed;
    }
}
