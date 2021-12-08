package bll;

import be.Playlist;
import dal.db.dao.PlaylistDAO_DB;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager {
    PlaylistDAO_DB playlistDAODB;

    public PlaylistManager()
    {
        playlistDAODB = new PlaylistDAO_DB();
    }

    public List<Playlist> getAllPlaylists() throws SQLException {
        return playlistDAODB.getAllPlaylists();
    }
}
