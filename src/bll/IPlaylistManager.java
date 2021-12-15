package bll;

import be.Playlist;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.List;

public interface IPlaylistManager {
    public List<Playlist> getAllPlaylists() throws SQLException;


    public Playlist createPlaylist(String name) throws SQLServerException, SQLServerException;


    public void updatePlaylist(Playlist playlist);


    public void deletePlaylist(Playlist playlist);
}
