package dal;

import be.Playlist;
import be.Song;

import java.sql.SQLException;
import java.util.List;

public interface IDALManager {

    public List<Song> getAllSongs();

    public List<Playlist> getAllPlaylists() throws SQLException;

    public Song getSingleSongById(int id);

    public Song createSong(String title, String artist, String category, String time, String URL) ;


    public void updateSong(Song song) ;


    public void deleteSong(Song song) ;


}
