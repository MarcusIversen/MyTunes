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

    /**
     * Her findes read deln af song databasen. Der laves en liste som henter alle sangene ned fra databasen.
     * Den henter id, title, artist, category, time og URL.
     * @return allSongs
     */
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
                    int time = resultset.getInt("Time");
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


    /**
     * Her hentes hver enkelte sangs id fra databasen, det bruger vi til at sætte den ind på playlisten senere.
     * @param id
     * @return null;
     */
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
                    int time = resultSet.getInt("Time");
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

    /**
     * Create delen af CRUD, her kan man oprette en ny sang med det info der bliver tildelt i appen.
     * Sange creates på dataene: title, artist, category, time og URL.3
     * @param title
     * @param artist
     * @param category
     * @param time
     * @param URL
     * @return
     */

    public Song createSong(String title, String artist, String category, int time, String URL) {

        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "INSERT INTO Songs(Title, Artist, Category, Time, URL) values(?,?,?,?,?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, artist);
                preparedStatement.setString(3, category);
                preparedStatement.setInt(4, time);
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


    /**
     * update delen af CRUD for songs, her opdateres sangen, igen på samme data som create.
     * @param song
     */
    public void updateSong(Song song) {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Songs SET Title=?, Artist=?, Category=?, Time=?, URL=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, song.getTitle());
            preparedStatement.setString(2, song.getArtist());
            preparedStatement.setString(3, song.getCategory());
            preparedStatement.setInt(4, song.getTime());
            preparedStatement.setString(5, song.getURL());
            preparedStatement.setInt(6, song.getId());
            if (preparedStatement.executeUpdate() != 1) {
                throw new Exception("Could not delete song");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * delete delen af CRUD, her slettes en sang, dette gør den gennem et Id.
     * @param song
     */
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
