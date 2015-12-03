package master.address;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private Main mainApp;

    @Override
    public void start(Stage primaryStage) throws Exception{
        mainApp = this;
        this.primaryStage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("view/ChatWindowView.fxml"));
        primaryStage.setTitle("CS242 Homework 3 Chat Program: Thanh-Phong Ho");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.setResizable(false);

        primaryStage.show();
    }
}
