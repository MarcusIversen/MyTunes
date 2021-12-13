package bll;

import be.Song;

import java.sql.SQLException;
import java.util.List;

public interface ISongsInPlaylistManager {

    public List<Song> getAllSongsInPlaylist(int PlaylistId) throws SQLException;

    public Song addSongToPlaylist(int PlaylistId, int SongId);

}
