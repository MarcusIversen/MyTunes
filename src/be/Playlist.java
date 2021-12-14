package be;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private int PlaylistId;
    private String name;
    private List<Song> songs = new ArrayList<>();
    private double time;

    public Playlist(int id, String name) {
        this.PlaylistId = id;
        this.name = name;
        //this.songs = songs;
        //this.time = time;

    }


    public int getSongCount() {
        return songs.size();
    }


  /*  public static double getPlayTime() {
        double playTime = 0;
        for (Song song : playlist) {
            playTime += song.getTime();
        }
        return playTime;
    }*/

    public int getPlaylistId() {
        return PlaylistId;
    }

    public void setPlaylistId(int playlistId) {
        this.PlaylistId = playlistId;
    }

    public String getName() {
        return name;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public double getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setTime(double time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "name='" + name + '\'' +
                ", songs=" + songs +
                ", time=" + time +
                '}';
    }
}
