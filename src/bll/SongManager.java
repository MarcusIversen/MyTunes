package bll;

import be.Song;
import dal.db.SongDAO_DB;

import java.util.List;

public class SongManager {

private SongSearcher songSearcher = new SongSearcher();

private SongDAO_DB songDAO_DB;

public SongManager() {
    songDAO_DB = new SongDAO_DB();
}

public List<Song> getAllSongs() throws Exception {
return songDAO_DB.getAllSongs();
}

public List<Song> searchSongs(String query) throws Exception{
    List<Song> allSongs = getAllSongs();
    List<Song> searchResult = songSearcher.search(allSongs, query);
    return searchResult;
}




}
