package be;

public class Song {
    private int id;
    private String title;
    private String artist;
    private String category;
    private String time;

    public Song(String title, String artist, String category, String time, int id, String URL) {

            this.title = title;
            this.artist = artist;
            this.category = category;
            this.time = time;
            this.URL = URL;
            this.id = id;
    }



    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    private String URL;

    public Song(int id, String title, String artist, String category, String time, String URL) {

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

    public String getTime() {
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


