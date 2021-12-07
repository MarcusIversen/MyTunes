package gui;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.IOException;


public class NewSong {

    public Button ReturnMainMenu;
    public TextField titleBar;
    public TextField artistBar;
    public TextField timeBar;
    public ComboBox <String> categoryMenu;
    public Button songSaveButton;
    public TextField fileText;


    public void initialize (){
        categoryMenu.setItems(FXCollections.observableArrayList("Pop", "Rock", "Reggae", "Techno", "RnB"));
    }

    public void GoReturnMainMenu(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) ReturnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);

    }



    public String getSongInfo(){
        String temp;
        temp = title() + "\n" + artist()  + "\n" + category() + "\n" + time();
        System.out.println(temp);
        return temp;
    }


    public String title(){
        String titleTemp = titleBar.getText();
        return titleTemp;

    }
    public String artist(){
       String artistTemp = artistBar.getText();
        return artistTemp;
    }
    public String category(){
        String categoryTemp = categoryMenu.getSelectionModel().getSelectedItem().toString();
        return categoryTemp;
    }
    public String time(){
        String temp = timeBar.getText();
        return temp;
    }

    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("mp3 files", "*.MP3"));
        if(selectedFile != null){
            fileText.appendText(selectedFile.getAbsolutePath());
        }else System.out.println("File is not valid");
    }

}
