package bll;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.DALManager;
import dal.IDALManager;
import dal.db.dao.PlaylistDAO_DB;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager implements IPlaylistManager {
    PlaylistDAO_DB playlistDAODB;
    private IDALManager dalManager;

    /**
     * Constructor til playlistmanageren
     */
    public PlaylistManager() {
        playlistDAODB = new PlaylistDAO_DB();
        dalManager = new DALManager();
    }

    /**
     * PLaylistmanager implements IPlaylistManager, derfor bruges alle metoder fra interfacet.
     * C.R.U.D
     * Creater playlist = create
     * getAllPLaylists = read
     * updatePlaylist = update
     * deletePlaylist = delete
     * @return
     * @throws SQLException
     */
    public List<Playlist> getAllPlaylists() throws SQLException {
        return dalManager.getAllPlaylists();

    }

    @Override
    public Playlist createPlaylist(String name) throws SQLServerException {
        return dalManager.createPlaylist(name);
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        dalManager.updatePlaylist(playlist);
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        dalManager.deletePlaylist(playlist);

    }


}
