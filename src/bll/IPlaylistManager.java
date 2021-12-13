package bll;

import be.Playlist;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistManager {
    public List<Playlist> getAllPlaylists() throws SQLException;


    public Playlist createPlaylist(String name) throws SQLServerException;


    public void updatePlaylist(Playlist Playlist);


    public void deletePlaylist(Playlist Playlist);
}
