package be;

public class Song {
    private String title;
    private String artist;
    private String category;
    private double time;

    public Song(String title, String artist, String category, double time) {

        this.title = title;
        this.artist = artist;
        this.category = category;
        this.time = time;
    }

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getArtist(){return artist;}

    public void setArtist(String artist){this.artist = artist;}

    public String getCategory(){return category;}

    public void setCategory(String category){this.category = category;}

    public Double getTime(){return time;}

    public void setTime(){this.time = time;}

    @Override
    public String toString()
    {
        return title + " , " +  artist + " , " + category + " , " + time;
    }
}


