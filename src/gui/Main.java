package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;

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

    }

}


