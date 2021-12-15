package dal;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.SQLException;
import java.util.List;

public interface IDALManager {

    public List<Song> getAllSongs();

    public List<Playlist> getAllPlaylists() throws SQLException;


    public Song getSingleSongById(int id);

    public Song createSong(String title, String artist, String category, String time, String URL) ;


    public void updateSong(Song song) ;


    public void deleteSong(Song song) ;

    Playlist createPlaylist(String name) throws SQLServerException;

    Song addSongToPLaylist(int playlistId, int songId);


    public void deletePlaylist(Playlist playlist);

    void deleteSongInPlaylist(int playlistId, int songId);

    public void updatePlaylist(Playlist playlist);

}
