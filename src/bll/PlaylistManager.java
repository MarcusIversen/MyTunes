package bll;

import be.Playlist;
import dal.db.PlaylistDbLogic;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager {
    PlaylistDbLogic playlistDbLogic;

    public PlaylistManager()
    {
        playlistDbLogic = new PlaylistDbLogic();
    }

    public List<Playlist> getAllPlaylists() throws SQLException {
        return playlistDbLogic.getAllPlaylists();
    }
}
