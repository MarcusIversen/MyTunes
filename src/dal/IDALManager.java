package dal;

import be.Song;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface IDALManager {

    public List<Song> getAllSongs();

    public Song getSingleSongById(int id);

    public Song createSong(String title, String artist, String category, double time) ;


    public void updateSong(Song song) ;


    public void deleteSong(Song song) ;
}
