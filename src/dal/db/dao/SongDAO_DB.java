package dal.db.dao;

import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongDAO_DB {

    private MyDatabaseConnector databaseConnector;

    public SongDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    public List<Song> getAllSongs() {
        ArrayList<Song> allSongs = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {

            String sql = "SELECT * FROM Songs;";

            Statement statement = connection.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultset = statement.getResultSet();
                while (resultset.next()) {
                    int id = resultset.getInt("Id");
                    String title = resultset.getString("Title");
                    String artist = resultset.getString("Artist");
                    String category = resultset.getString("Category");
                    String time = resultset.getString("Time");
                    String URL = resultset.getString("URL");


                    Song song = new Song(id, title, artist, category, time, URL);
                    allSongs.add(song);
                }
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allSongs;
    }

    public Song getSingleSongById(int id) {
        try (Connection connection = databaseConnector.getConnection()) {

            String sql = "SELECT * FROM Songs WHERE Id=?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                        id = resultSet.getInt("Id");
                        String title = resultSet.getString("Title");
                        String artist = resultSet.getString("Artist");
                        String category = resultSet.getString("Category");
                        String time = resultSet.getString("Time");
                        String URL = resultSet.getString("URL");


                        Song song = new Song(id, title, artist, category, time, URL);
                        return song;

                }
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }




    public Song createSong(String title, String artist, String category, String time, String URL) {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Songs(Title, Artist, Category, Time, URL) values(?,?,?,?,?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, artist);
                preparedStatement.setString(3, category);
                preparedStatement.setString(4, time);
                preparedStatement.setString(5, URL);
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                int id = 0;
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
                Song song = new Song(id, title, artist, category, time, URL);
                return song;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public void updateSong(Song song) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Songs SET Title=?, Artist=?, Category=?, Time=?, URL=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getArtist());
            preparedStatement.setString(3, song.getCategory());
            preparedStatement.setString(4, song.getTime());
            preparedStatement.setString(5, song.getURL());
            preparedStatement.setInt(6, song.getId());
            if (preparedStatement.executeUpdate() != 1) {
                throw new Exception("Could not delete song");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteSong(Song song) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Songs WHERE Id =?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, song.getId());
            if (preparedStatement.executeUpdate() != 1) {
                throw new Exception("Could not delete song");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
