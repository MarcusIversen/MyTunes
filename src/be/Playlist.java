package be;

import java.util.List;

public class Playlist {

    private int PlaylistId;
    private String name;
    private int songs;
    private double time;
    private static List<Playlist> playlist;

    public Playlist(String name) {

        this.name = name;
        //this.songs = songs;
        //this.time = time;

    }


    public static int getSongCount() {
        return playlist.size();
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

    public int getSongs() {
        return songs;
    }

    public double getTime() {
        return time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSongs(int songs) {
        this.songs = songs;
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
