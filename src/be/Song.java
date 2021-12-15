package be;

import java.sql.Time;

public class Song {

    private int id;
    private int songsInPlaylistId;
    private int songId;
    private int playlistId;
    private String title;
    private String artist;
    private String category;
    private int time;
    private String URL;


    public Song(int songsInPlaylistId, int songId, int playlistId) {
        this.songsInPlaylistId = songsInPlaylistId;
        this.songId = songId;
        this.playlistId = playlistId;
    }

    public Song(int id, String title, String artist, String category, int time, String URL) {

        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.time = time;
        this.URL = URL;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTime() {
        return time;
    }

    public void setTime() {
        this.time = time;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDuration() {
        int minuts = (time)/60;
        int seconds = (time)%60;
        if (10 > seconds){
            return minuts + ":0" + seconds;
        } else {
            return minuts + ":" + seconds;
        }

    }
    @Override
    public String toString() {
        return title + " - " + artist ;
    }

}


