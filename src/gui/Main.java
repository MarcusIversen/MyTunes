package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
<<<<<<< HEAD
import javafx.stage.Stage;

=======
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
>>>>>>> c9d25fb007eb19025eaabcb5c08db31a05d5de5c

import javax.swing.*;

<<<<<<< HEAD
    public static void main(String[] args) {
=======
public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        primaryStage.setTitle("Converter of Fun");
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    public static void main(String[] args)
    {

>>>>>>> c9d25fb007eb19025eaabcb5c08db31a05d5de5c
    }

}


