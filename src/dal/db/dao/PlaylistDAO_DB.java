package dal.db.dao;

import be.Playlist;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistDAO_DB {

    private MyDatabaseConnector databaseConnector;
    private SongsInPlaylistDAO_DB songsInPlaylistDAODb;
    public PlaylistDAO_DB()
    {
        databaseConnector = new MyDatabaseConnector();
        songsInPlaylistDAODb =  new SongsInPlaylistDAO_DB();
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

                    int id = resultset.getInt("PlaylistId");
                   String name = resultset.getString("Name");


                    Playlist playlist = new Playlist(id, name);
                    playlist.setSongs(songsInPlaylistDAODb.getAllSongsInPlaylist(id));
                    allPlaylist.add(playlist);
                }
            }
        }
        return allPlaylist;
    }

   public Playlist createPlaylist(String name) throws SQLServerException {

        try(Connection connection = databaseConnector.getConnection())
        {
            String sql = "INSERT INTO PLaylist(Name) values(?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, name);
                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                int id = 0;
                if (resultSet.next()) {
                    id = resultSet.getInt(1);
                }
                Playlist playlist = new Playlist(id, name);
                return playlist;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }




    public void updatePlaylist(Playlist playlist) throws SQLException {
        try (Connection connection = databaseConnector.getConnection()) {
            String sql = "UPDATE Playlist SET Name=? WHERE PlaylistId=?;";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, playlist.getName());
            preparedStatement.setInt(2, playlist.getPlaylistId());
            if (preparedStatement.executeUpdate() != 1) {
                throw new Exception("Could not edit song");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void deletePlaylist (Playlist playlist){
        String sql = "DELETE FROM Playlist WHERE PlaylistId =?;";
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, playlist.getPlaylistId());
            if (preparedStatement.executeUpdate() != 1) {
                throw new Exception("Could not delete playlist");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
