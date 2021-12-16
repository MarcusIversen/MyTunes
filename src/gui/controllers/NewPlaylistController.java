package gui.controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import gui.models.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class NewPlaylistController {

    public TextField TextInputPlaylist;
    private PlaylistModel playlistModel;
    public Button BackMainMenu;

    /**
     * Constructor til NewPlaylistController, hvor variabler bliver instancieret.
     * @throws SQLException
     */
    public NewPlaylistController() throws SQLException {
        playlistModel = new PlaylistModel();
    }

    /**
     * Metode til at skifte tilbage til mainMenu stage.
     * @param actionEvent
     * @throws IOException
     */
    public void GoBackMainMenu(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) BackMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    /**
     * Metode der gemmer playlist.
     * Derudover skifter den også stage til mainMenu, efter der er trykket save.
     * @param event
     * @throws IOException
     * @throws SQLServerException
     */
    public void savePlaylist(ActionEvent event) throws IOException, SQLServerException {
        uploadPlaylistInfo(TextInputPlaylist.getText());

        Stage swich = (Stage) BackMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);

    }

    /**
     * Metode der er med til at uploade playlistDataen til vores model layer.
     * @param name
     * @throws SQLServerException
     */
    public void uploadPlaylistInfo(String name) throws SQLServerException {
        playlistModel.createPLaylist(name);
    }
}
