package bll;

import be.Song;

import java.util.List;

public interface ISongManager {
    public List<Song> getAllSongs();

    public Song getSingleSongById(int id);

    public Song createSong(String title, String artist, String category, int time, String URL);

    public void updateSong(Song song);


    public void deleteSong(Song song);

    public List<Song> searchSongs(String query);

}
