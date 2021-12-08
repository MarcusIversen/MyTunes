import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application{


    public void start(Stage primaryStage) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("gui/view/MainMenu.fxml"));
        primaryStage.setTitle("myTunes");
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) { launch(args);}
}


