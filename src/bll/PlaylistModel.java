package bll;

import be.Playlist;
import be.Song;
import bll.PlaylistManager;
import bll.SongManager;
import gui.MainMenuController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PlaylistModel {
    private ObservableList<Playlist> playlistToBeViewed;

    private PlaylistManager playlistManager;

    public PlaylistModel() throws Exception {
        playlistManager = new PlaylistManager();
        playlistToBeViewed = FXCollections.observableArrayList();
        playlistToBeViewed.addAll(playlistManager.getAllPlaylists());
    }

    public ObservableList<Playlist> getPlaylistData(){
        return playlistToBeViewed;
    }
}
