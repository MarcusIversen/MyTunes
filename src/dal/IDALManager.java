package dal;

import be.Song;

import java.util.List;

public interface IDALManager {

    public List<Song> getAllSongs();

    public Song getSingleSongById(int id);

    public Song createSong(String title, String artist, String category, String time, String URL) ;


    public void updateSong(Song song) ;


    public void deleteSong(Song song) ;
}
