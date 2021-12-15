package bll;

import be.Song;
import dal.DALManager;
import dal.IDALManager;
import dal.db.dao.SongsInPlaylistDAO_DB;

import java.sql.SQLException;
import java.util.List;

public class SongsInPlaylistManager implements ISongsInPlaylistManager {
    SongsInPlaylistDAO_DB songsInPlaylistDAO;
    private IDALManager dalManager ;

    public SongsInPlaylistManager()
    {
        songsInPlaylistDAO = new SongsInPlaylistDAO_DB();
        dalManager = new DALManager();
    }


    @Override
    public Song addSongToPlaylist(int PlaylistId, int SongId) {
        return dalManager.addSongToPLaylist(PlaylistId, SongId);
    }

    public void deleteSongInPlaylist(int PlaylistId, int SongId){
        dalManager.deleteSongInPlaylist(PlaylistId, SongId);
    }
}
