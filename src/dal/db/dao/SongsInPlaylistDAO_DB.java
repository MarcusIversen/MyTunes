package dal.db.dao;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongsInPlaylistDAO_DB {
    private MyDatabaseConnector databaseConnector;

    public SongsInPlaylistDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    public List<Song> getAllSongsInPlaylist(int PlaylistId) {
        ArrayList<Song> allSongsInPlaylist = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {

            String sql = "SELECT * FROM Songs INNER JOIN SongsInPlaylist ON SongsInPlaylist.SongId = Songs.Id WHERE SongsInPlaylist.PlaylistId = ?;";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, PlaylistId);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while(rs.next()){
                String name = rs.getString("Title");
                String artist = rs.getString("Artist");
                String category = rs.getString("Category");
                String time = rs.getString("Time");
                int id = rs.getInt("Id");
                String url = rs.getString("URL");
                if(url != null)
                    allSongsInPlaylist.add(new Song(name, artist, category, time, id, url));

            }
            return allSongsInPlaylist;
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allSongsInPlaylist;
    }


    public void addSongToPlaylist(int PlaylistId, int SongId){
        try (Connection connection = databaseConnector.getConnection()) {

            String sql = "INSERT INTO SongsInPlaylist (PlaylistId, SongId) VALUES (?,?);";
            System.out.println(PlaylistId + "" + SongId);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1,PlaylistId);
                preparedStatement.setInt(2,SongId);
                preparedStatement.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void deleteSongInPlaylist (int PlaylistId, int SongId){
        String sql = "DELETE FROM SongsInPlaylist WHERE PlaylistId =? AND SongId =?;";
        try (Connection connection = databaseConnector.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, PlaylistId);
            preparedStatement.setInt(2, SongId);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}