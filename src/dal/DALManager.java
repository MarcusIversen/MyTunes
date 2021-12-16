package dal;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.dao.PlaylistDAO_DB;
import dal.db.dao.SongDAO_DB;
import dal.db.dao.SongsInPlaylistDAO_DB;

import java.sql.SQLException;
import java.util.List;

public class DALManager implements IDALManager {
    private SongDAO_DB songDAO;
    private PlaylistDAO_DB playlistDAO;
    private SongsInPlaylistDAO_DB songsInPlaylistDAO;


    /**
     * Constructor til dalManager klassen.
     */
    public DALManager() {
        songDAO = new SongDAO_DB();
        playlistDAO = new PlaylistDAO_DB();
        songsInPlaylistDAO = new SongsInPlaylistDAO_DB();
    }

    /**
     * Alt Data access layer CRUD tages i brug her, dette er et led i vores trelagsarkitektur.
     * @return
     */

    @Override
    public List<Song> getAllSongs() {
        return songDAO.getAllSongs();
    }

    @Override
    public List<Playlist> getAllPlaylists() throws SQLException {
        return playlistDAO.getAllPlaylists();
    }


    public List<Song> getAllSongsInPlaylist(int PlaylistId) throws SQLException {
        return songsInPlaylistDAO.getAllSongsInPlaylist(PlaylistId);
    }


    public Playlist createPlaylist(String name) throws SQLServerException {
        return playlistDAO.createPlaylist(name);
    }

    @Override
    public Song addSongToPLaylist(int playlistId, int songId) {
        songsInPlaylistDAO.addSongToPlaylist(playlistId, songId);
        return null;
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        try {
            playlistDAO.updatePlaylist(playlist);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteSongInPlaylist(int PlaylistId, int SongId) {
        songsInPlaylistDAO.deleteSongInPlaylist(PlaylistId, SongId);
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        playlistDAO.deletePlaylist(playlist);
    }

    @Override
    public Song getSingleSongById(int id) {
        return songDAO.getSingleSongById(id);
    }

    @Override
    public Song createSong(String title, String artist, String category, int time, String URL) {
        return songDAO.createSong(title, artist, category, time, URL);
    }

    @Override
    public void updateSong(Song song) {
        songDAO.updateSong(song);

    }

    @Override
    public void deleteSong(Song song) {
        songDAO.deleteSong(song);
    }
}
