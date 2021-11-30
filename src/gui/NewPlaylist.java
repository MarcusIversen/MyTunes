package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class NewPlaylist {

    public Button BackMainMenu;

    public void GoBackMainMenu(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) BackMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);




    }

}
