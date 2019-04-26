package ro.utcluj.sd.parking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent fxml = FXMLLoader.load(getClass().getResource("/MyView.fxml"));
        Scene scene = new Scene(fxml);
        primaryStage.sizeToScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

//    public static void main(String[] args) throws IOException {
//        Socket clientSocket = new Socket("127.0.0.1", 1997);
//
//        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(clientSocket.getInputStream()));
//
//        out.println("Hello from Client2!");
//        String fromServer = in.readLine();
//        System.out.println(fromServer);
//
//        clientSocket.close();
//        out.close();
//        in.close();
//    }
}
