package ro.utcluj.sd.parking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent fxml = FXMLLoader.load(getClass().getResource("/MyView.fxml"));
        Scene scene = new Scene(fxml);
        primaryStage.setWidth(900);
        primaryStage.setHeight(400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
