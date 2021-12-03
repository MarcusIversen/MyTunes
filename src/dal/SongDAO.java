package dal;

import be.Song;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.MyDatabaseConnector;
import dal.db.SongDAO_DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SongDAO implements ISongDataAccess {

    @Override
    public List<Song> getAllSongs() throws Exception {
        return null;
    }

    @Override
    public Song createSong(int id, String title, String Artist, String Category, double time) throws Exception {
        return null;
    }

    @Override
    public void updateSong(Song song) throws Exception {

    }

    @Override
    public void deleteSong(Song song) throws Exception {

    }
}
