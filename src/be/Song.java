package be;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String category;
    private double time;

    public Song(int id, String title, double time) {

        this.id = id;
        this.title = title;
        this.artist = artist;
        this.category = category;
        this.time = time;
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

    public String getArtist() {return artist;}

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getTime() {
        return time;
    }

    public void setTime() {
        this.time = time;
    }

    @Override
    public String toString() {
        return id + " , " + title + " , " + artist + " , " + category + " , " + time;
    }
}


