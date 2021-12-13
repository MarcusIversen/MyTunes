package bll;

import be.Playlist;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistManager {
    public List<Playlist> getAllPlaylists() throws SQLException;


    public Playlist createPlaylist(String name, int songs, double time);


    public void updatePlaylist(Playlist Playlist);


    public void deletePlaylist(Playlist Playlist);
}
