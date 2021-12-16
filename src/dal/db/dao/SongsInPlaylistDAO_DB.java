package dal.db.dao;

import be.Playlist;
import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SongsInPlaylistDAO_DB {
    private final MyDatabaseConnector databaseConnector;

    public SongsInPlaylistDAO_DB() {
        databaseConnector = new MyDatabaseConnector();
    }

    /**
     * Read delen af CRUD, her laves der en liste, hvor alle sange på en playlist bliver hentet.
     * @param PlaylistId
     * @return allsongsinplaylist
     */

    public List<Song> getAllSongsInPlaylist(int PlaylistId) {
        ArrayList<Song> allSongsInPlaylist = new ArrayList<>();

        try (Connection connection = databaseConnector.getConnection()) {

            String sql = "SELECT * FROM Songs INNER JOIN SongsInPlaylist ON SongsInPlaylist.SongId = Songs.Id WHERE SongsInPlaylist.PlaylistId = ?;";
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, PlaylistId);
            statement.execute();
            ResultSet rs = statement.getResultSet();
            while (rs.next()) {
                String name = rs.getString("Title");
                String artist = rs.getString("Artist");
                String category = rs.getString("Category");
                int time = rs.getInt("Time");
                int id = rs.getInt("Id");
                String url = rs.getString("URL");
                if (url != null)
                    allSongsInPlaylist.add(new Song(id, name, artist, category, time, url));

            }
            return allSongsInPlaylist;
        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allSongsInPlaylist;
    }


    /**
     * her er Create delen af CRUD, hvor du kan create og tilføje en sang til vores playlist(s).
     * @param PlaylistId
     * @param SongId
     */

    public void addSongToPlaylist(int PlaylistId, int SongId) {
        try (Connection connection = databaseConnector.getConnection()) {

            String sql = "INSERT INTO SongsInPlaylist (PlaylistId, SongId) VALUES (?,?);";
            System.out.println(PlaylistId + "" + SongId);

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setInt(1, PlaylistId);
                preparedStatement.setInt(2, SongId);
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

    /**
     * delete delen af CRUD, hvor du kan slette en sang fra playlisten.
     * @param PlaylistId
     * @param SongId
     */

    public void deleteSongInPlaylist(int PlaylistId, int SongId) {
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