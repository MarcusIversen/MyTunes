package gui.models;

import be.Playlist;
import bll.PlaylistManager;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public class PlaylistModel {
    private ObservableList<Playlist> playlistToBeViewed;

    private PlaylistManager playlistManager;

    public PlaylistModel() throws SQLException {
        playlistManager = new PlaylistManager();
        playlistToBeViewed = FXCollections.observableArrayList();
        playlistToBeViewed.addAll(playlistManager.getAllPlaylists());
    }

    public List<Playlist> getPlaylist() throws SQLException{
        List<Playlist> allPlaylist = playlistManager.getAllPlaylists();
        return allPlaylist;
    }

    public ObservableList<Playlist> getPlaylistData() throws SQLException {
        return playlistToBeViewed;
    }

    public void createPLaylist(String name) throws SQLServerException {
        Playlist newPlaylist = playlistManager.createPlaylist(name);
        playlistToBeViewed.add(newPlaylist);
    }

    public void deletePlaylist(Playlist playlist){
     playlistManager.deletePlaylist(playlist);}

    public void updatePlaylist(Playlist playlist){
        playlistManager.updatePlaylist(playlist);

    }
}
