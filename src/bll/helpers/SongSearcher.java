package bll.helpers;

import be.Song;

import java.util.ArrayList;
import java.util.List;

public class SongSearcher {

    /**
     * Metode der bruges til at søge på en sang gennem titlen.
     * @param searchBase
     * @param query
     * @return
     */

    public List<Song> search(List<Song> searchBase, String query) {
        List<Song> searchResult = new ArrayList<>();

        for (Song song : searchBase) {
            if (compareToSongTitle(query, song)) {
                searchResult.add(song);
            }
        }

        return searchResult;
    }


    /**
     * Her sammenligner vi titler på sangene, for at se hvad de indholder.
     * @param query
     * @param song
     * @return
     */
    private boolean compareToSongTitle(String query, Song song) {
        return song.getTitle().toLowerCase().contains(query.toLowerCase());
    }
}
