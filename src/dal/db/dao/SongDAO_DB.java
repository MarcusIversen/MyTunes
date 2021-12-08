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
                    double time = resultset.getDouble("Time");


                    Song song = new Song(id, title, artist, category, time);
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

    public int getSingleSongById(int id) {
        //TODO CONNECT TO GET FILE METHOD
        try (Connection connection = databaseConnector.getConnection()) {

            String sql = "SELECT * FROM Songs;";

            Statement statement = connection.createStatement();

            if (statement.execute(sql)) {
                ResultSet resultset = statement.getResultSet();
                while (resultset.next()) {
                    id = resultset.getInt("Id");
                    String title = resultset.getString("Title");
                    String artist = resultset.getString("Artist");
                    String category = resultset.getString("Category");
                    double time = resultset.getDouble("Time");


                    Song song = new Song(id, title, artist, category, time);
                }
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return id;
    }



    public Song createSong(String title, String artist, String category, double time) {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Songs(Title, Artist, Category, Time) values(?,?,?,?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, artist);
                preparedStatement.setString(3, category);
                preparedStatement.setDouble(4, time);
                preparedStatement.executeUpdate();
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                int id = 0;
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
                Song song = new Song(id, title, artist, category, time);
                return song;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }


    public void updateSong(Song song) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Song SET Title=?, Artist=?, Category=?, Time=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getArtist());
            preparedStatement.setString(3, song.getCategory());
            preparedStatement.setDouble(4, song.getTime());
            if (preparedStatement.executeUpdate() != 1) {
                throw new Exception("Could not delete song");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void deleteSong(Song song) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "DELETE FROM Song WHERE Id =?;";
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
