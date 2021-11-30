package dal.db;

import be.Song;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SongDAO_DB {

   private MyDatabaseConnector databaseConnector;

    public SongDAO_DB()
    {
        databaseConnector = new MyDatabaseConnector();
    }
        public List<Song> getAllSongs() throws SQLException {
            ArrayList<Song> allSongs = new ArrayList<>();

            try(Connection connection = databaseConnector.getConnection())
            {
                String sql = "SELECT * FROM Songs;";

                Statement statement = connection.createStatement();

                if(statement.execute(sql))
                {
                    ResultSet resultset = statement.getResultSet();
                    while(resultset.next())
                    {
                        String title = resultset.getString("Title");
                        String artist = resultset.getString("Artist");
                        String category = resultset.getString("Category");
                        Double time = resultset.getDouble("Time");

                        Song song = new Song(title, artist, category, time);
                        allSongs.add(song);
                    }
                }
            }
            return allSongs;

        }

    public static void main(String[] args) throws SQLException {
        SongDAO_DB songDAO_db = new SongDAO_DB();

        List<Song> allSongs = songDAO_db.getAllSongs();

        System.out.println(allSongs);
    }
}
