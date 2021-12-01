package dal;

import be.Song;

import java.util.List;

public interface ISongDataAccess {

    public List<Song> getAllSongs() throws Exception;

    public Song createSong(int id, String title, String Artist, String Category, double time) throws Exception;

    public void updateSong(Song song) throws Exception;

    public void deleteSong(Song song) throws Exception;

}

