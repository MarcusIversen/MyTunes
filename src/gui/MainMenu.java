package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenu{

    public Button NewPlaylist;

    public void onBmiClicked(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) NewPlaylist.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("NewPlaylist .fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);




    }


}
