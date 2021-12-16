package bll;

import be.Song;
import bll.helpers.SongSearcher;
import dal.DALManager;
import dal.IDALManager;

import java.util.List;

public class SongManager implements ISongManager {

    private SongSearcher songSearcher;
    private IDALManager dalManager;


    /**
     * Constructer til SongManager.
     */
    public SongManager() {
        songSearcher = new SongSearcher();
        dalManager = new DALManager();
    }


    /**
     * C.R.U.D til Song
     * createSong = Create
     * GetSingleSongById, getAllSongs = read
     * updateSong = update
     * deleteSong = delete
     * @return
     */
    @Override
    public List<Song> getAllSongs() {
        return dalManager.getAllSongs();
    }

    @Override
    public Song getSingleSongById(int id) {
        return dalManager.getSingleSongById(id);
    }

    @Override
    public Song createSong(String title, String artist, String category, int time, String URL) {
        return dalManager.createSong(title, artist, category, time, URL);
    }

    @Override
    public void updateSong(Song song) {
        dalManager.updateSong(song);
    }

    @Override
    public void deleteSong(Song song) {
        dalManager.deleteSong(song);
    }


    /**
     * Her tages serachsongs i brug.
     * @param query
     * @return searchResult
     */
    public List<Song> searchSongs(String query) {
        List<Song> allSongs = getAllSongs();
        List<Song> searchResult = songSearcher.search(allSongs, query);
        return searchResult;
    }
}



