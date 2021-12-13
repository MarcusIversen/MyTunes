package dal.db.dao;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO_DB {

    private MyDatabaseConnector databaseConnector;

    public PlaylistDAO_DB()
    {
        databaseConnector = new MyDatabaseConnector();
    }
    public List<Playlist> getAllPlaylists() throws SQLException {
        ArrayList<Playlist> allPlaylist = new ArrayList<>();

        try(Connection connection = databaseConnector.getConnection())
        {

            String sql = "SELECT * FROM Playlist;";

            Statement statement = connection.createStatement();

            if(statement.execute(sql))
            {
                ResultSet resultset = statement.getResultSet();
                while(resultset.next())
                {
                   String name = resultset.getString("Name");
                   int songs = resultset.getInt("Songs");
                   double time = resultset.getDouble("Time");


                    Playlist playlist = new Playlist(name, songs, time);
                    allPlaylist.add(playlist);
                }
            }
        }
        return allPlaylist;
    }

   public Playlist createPlaylist(String name, int songs, double time) throws SQLServerException {

        try(Connection connection = databaseConnector.getConnection())
        {
            String sql = "INSERT INTO PLaylist(Name, Songs, Time) values(?,?,?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, songs);
                preparedStatement.setDouble(3, time);
                preparedStatement.executeUpdate();

                Playlist playlist = new Playlist(name, songs, time);
                return playlist;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }



    /**
     * public void updatePlaylist(Playlist playlist) throws SQLException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Playlist SET Name=?, Songs=?, Time=? WHERE Id=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Playlist.getPlName());
            preparedStatement.setString(2, String.valueOf(Playlist.getSongCount()));
            preparedStatement.setDouble(3, Playlist.getPlTime());
            if (preparedStatement.executeUpdate() != 1) {
                throw new Exception("Could not delete song");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     **/


    public void deleteSong (Song song){
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
