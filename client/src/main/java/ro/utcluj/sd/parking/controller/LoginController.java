/*************************************************************************
 * ULLINK CONFIDENTIAL INFORMATION
 * _______________________________
 *
 * All Rights Reserved.
 *
 * NOTICE: This file and its content are the property of Ullink. The
 * information included has been classified as Confidential and may
 * not be copied, modified, distributed, or otherwise disseminated, in
 * whole or part, without the express written permission of Ullink.
 ************************************************************************/
package ro.utcluj.sd.parking.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ro.utcluj.sd.RequestToServer;
import ro.utcluj.sd.dto.UserDTO;
import ro.utcluj.sd.parking.command.LoginCommand;
import ro.utcluj.sd.parking.command.SubscribeCommand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginController {
    @FXML
    public PasswordField password;
    @FXML
    public TextField username;
    @FXML
    public Label label;

    public void login(ActionEvent actionEvent) {
        new LoginCommand(label, username.getText(), password.getText()).execute();
    }

    public void assign(ActionEvent actionEvent) {
        RequestToServer myRequest = new RequestToServer();
        myRequest.getParams().put("command", "assignParkingSpot");
        myRequest.getParams().put("parkingLotId", "1");

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(myRequest);
        System.out.println(json);

        try (Socket clientSocket = new Socket("127.0.0.1", 1997)) {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(json);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
