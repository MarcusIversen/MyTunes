package be;

import java.util.List;

public class Playlist {

    private int id;
    private String title;
    private List<Song> songs;

    public Playlist(int id, String title) {

        this.id = id;
        this.title = title;
    }

    public int getSongCount(){
        return songs.size();
    }

    public double getPlayTime(){
        double playTime = 0;
        for(Song song : songs){
            playTime += song.getTime();
        }
        return playTime;
    }

}
