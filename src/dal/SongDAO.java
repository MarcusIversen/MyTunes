package dal;

import be.Song;
import dal.db.SongDAO_DB;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SongDAO implements ISongDataAccess {

    private static final String SONGS_FILE = "MYTUNES2021GR7";
    private List<Song> allSongs;

    @Override
    public List<Song> getAllSongs() throws IOException {
        List<Song> allSongList = new ArrayList<>();

        File songsFile = new File(SONGS_FILE);


        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(songsFile))) {
            boolean hasLines = true;
            while (hasLines) {
                String line = bufferedReader.readLine();
                hasLines = (line != null);
                if (hasLines && !line.isBlank())
                {
                    String[] separatedLine = line.split(",");

                    int id = Integer.parseInt(separatedLine[0]);
                    String title = separatedLine[1];
                    String artist = separatedLine[2];
                    String category = separatedLine[3];
                    double time = Double.parseDouble(separatedLine[4]);
                    if(separatedLine.length > 5)
                    {
                        for(int i = 5; i < separatedLine.length; i++)
                        {
                            title += "," + separatedLine[i];
                        }
                    }
                    Song song = new Song(id, title, artist, category, time);
                    allSongList.add(song);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allSongList;
    }


    @Override
    public Song createSong(int id, String title, String artist, String category, double time) throws Exception {
        try(FileWriter writer = new FileWriter(SONGS_FILE, true);
            BufferedWriter bw = new BufferedWriter(writer))
        {
            int i = allSongs.get(allSongs.size()-1).getId()+1;
            Song s = new Song(i, title, artist, category, time);
            bw.append(i+ title + "," + artist + "," + category + "," + time + "\r\n");
            allSongs.add(s);
            return s;
        }
    }

    @Override
    public void updateSong(Song song) throws Exception {
        try{
            File tmp = new File(song.hashCode() + ".txt");
            List<Song> allSongs = getAllSongs();
            allSongs.removeIf((Song t) -> t.getId() == song.getId());
            allSongs.add(song);

            allSongs.sort(Comparator.comparingInt(Song::getId));

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(tmp))) {
                for (Song sn : allSongs) {
                    bw.write(sn.getId() + "," + sn.getTitle() + "," + sn.getArtist() + "," + sn.getCategory() + "," + sn.getTime());
                    bw.newLine();
                }
            }

            Files.copy(tmp.toPath(), new File(SONGS_FILE).toPath(), StandardCopyOption.REPLACE_EXISTING);

            Files.delete(tmp.toPath());

        }catch (IOException ex){

        }
    }

    @Override
    public void deleteSong(Song song) throws Exception {

    }
}
