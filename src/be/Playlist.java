package be;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private int PlaylistId;
    private String name;
    private double time;
    private List<Song> songs = new ArrayList<>();


    public Playlist(int id, String name) {
        this.PlaylistId = id;
        this.name = name;
    }

    /**
     * getDuration sørger for at tælle hver eneste sangs varighed og så lægges det sammen på playlisten.
     * @return minuts og seconds
     */
    public String getDuration() {
        int totalTime = 0;
        for (Song baseS : songs) {
            totalTime += baseS.getTime();
        }
        int minuts = totalTime / 60;
        int seconds = totalTime % 60;
        if (10 > seconds) {
            return minuts + ":0" + seconds;
        } else {
            return minuts + ":" + seconds;
        }

    }

    /**
     * Metode til at få fat i antallet af sange på playlisten
     * @return songs.size();
     */
    public int getSongCount() {
        return songs.size();
    }

    /**
     * Get og setter metoder til vores playliste.
     * @return
     */

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
