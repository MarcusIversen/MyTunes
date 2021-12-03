package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;

public class NewPlaylist {

    public Button BackMainMenu;

    public void GoBackMainMenu(ActionEvent actionEvent) throws IOException {

        Stage swich = (Stage) BackMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    @FXML
    private Label NamePrompt;
    @FXML
    private TextField TextInputPlaylist;
    @FXML
    private Button PlaylistSaveButton;

    String playlist;
    

    public void savePlaylist(ActionEvent event) {
        playlist = TextInputPlaylist.getText();
        System.out.println(playlist);
    }





/*
    public void savePlaylist()
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream("playlist");

            ObjectOutputStream outObjectStream = new ObjectOutputStream(fileOutputStream);

            outObjectStream.writeObject(playlist);

            outObjectStream.flush();
            outObjectStream.close();
        }
        catch(FileNotFoundException fnfException)
        {
            System.out.println("No file");
        }
        catch(IOException ioException)
        {
            System.out.println("bad IO");
        }

    }

    //Reads the file contents containing to a Playlist.
    public void loadPlaylist()
    {
        try
        {
            FileInputStream fileInputStream = new FileInputStream("playlist");

            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            playlist = (Playlist)objectInputStream.readObject();

            objectInputStream.close();
        }
        catch(FileNotFoundException fnfException)
        {
            System.out.println("No File");
        }
        catch(IOException ioException)
        {
            System.out.println("IO no good");
        }

        catch(ClassNotFoundException cnfException)
        {
            System.out.println("This is not a Playlist.");
        }

    }*/

}
