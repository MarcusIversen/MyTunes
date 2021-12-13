package bll;

import be.Playlist;
import be.Song;
import dal.DALManager;
import dal.IDALManager;
import dal.db.dao.PlaylistDAO_DB;

import java.sql.SQLException;
import java.util.List;

public class PlaylistManager implements IPlaylistManager{
    PlaylistDAO_DB playlistDAODB;
    private IDALManager dalManager ;

    public PlaylistManager()
    {
        playlistDAODB = new PlaylistDAO_DB();
        dalManager = new DALManager();
    }

    public List<Playlist> getAllPlaylists() throws SQLException {
            return dalManager.getAllPlaylists();

    }



    @Override
    public Playlist createPlaylist(String name, int songs, double time) {
        return null;
    }

    @Override
    public void updatePlaylist(Playlist Playlist) {

    }

    @Override
    public void deletePlaylist(Playlist Playlist) {

    }


}
