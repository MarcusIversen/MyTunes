package dal;

import be.Song;
import dal.db.dao.SongDAO_DB;

import java.util.List;

public class DALManager implements  IDALManager{
    private SongDAO_DB songDAO;

    public DALManager() {
        songDAO = new SongDAO_DB();
    }

    @Override
    public List<Song> getAllSongs() {
        return songDAO.getAllSongs();
    }

    @Override
    public Song getSingleSongById(int id) {
        return null;
    }

    @Override
    public Song createSong(String title, String artist, String category, double time) {
        return songDAO.createSong(title, artist, category, time);
    }

    @Override
    public void updateSong(Song song) {

    }

    @Override
    public void deleteSong(Song song) {

    }
}
