package gui.controllers;

import be.Playlist;
import gui.models.PlaylistModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditPlaylistController implements Initializable {

    public TextField TextInputPlaylist;
    public TextField playlistId;
    public Button BackMainMenu;
    public Button editButton;

    PlaylistModel playlistModel;

    /**
     * Constructor til EditPlaylistControlleren
     * @throws SQLException
     */
    public EditPlaylistController() throws SQLException {
        playlistModel = new PlaylistModel();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Her sættes en playlist, så når vi skal edit, er textfield allerede udfyldt med playlistens nuværende navn.
     * @param playlist
     */
    public void setPlaylist(Playlist playlist) {
        TextInputPlaylist.setText(playlist.getName());
        playlistId.setText(Integer.toString(playlist.getPlaylistId()));
    }

    /**
     * Metod til at skifte tilbage til mainMenu vinduet.
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
     * OnAction knappen der gør at vi kan edit en playlist, her udfylder den textfielded, så man ændrer på det rigtige
     * navn og playlist.
     * @param actionEvent
     * @throws IOException
     */
    public void editPlaylist(ActionEvent actionEvent) throws IOException {

        int updatePlaylistId = Integer.parseInt(playlistId.getText());
        String updatePlaylistName = TextInputPlaylist.getText();

        Playlist playlist = new Playlist(updatePlaylistId, updatePlaylistName);
        playlistModel.updatePlaylist(playlist);

        GoBackMainMenu(actionEvent);

    }
}
