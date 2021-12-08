package bll;

import be.Song;
import bll.helpers.SongSearcher;
import dal.DALManager;
import dal.IDALManager;
import dal.db.dao.SongDAO_DB;
import gui.controllers.NewSongController;

import java.sql.SQLException;
import java.util.List;

public class SongManager implements ISongManager {

    private SongSearcher songSearcher ;
    private IDALManager dalManager ;


    public SongManager() {
        songSearcher = new SongSearcher();
        dalManager = new DALManager();
    }


    @Override
    public List<Song> getAllSongs() {
        return dalManager.getAllSongs();
    }

    @Override
    public Song getSingleSongById(int id) {
        return dalManager.getSingleSongById(id);
    }

    @Override
    public Song createSong(String title, String artist, String category, double time) {
        return dalManager.createSong(title, artist, category, time);
    }

    @Override
    public void updateSong(Song song) {
         dalManager.updateSong(song);
    }

    @Override
    public void deleteSong(Song song) {
        dalManager.deleteSong(song);
    }

    public List<Song> searchSongs(String query) {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }
}



