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
package ro.utcluj.sd.parking.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Label;
import ro.utcluj.sd.RequestToServer;
import ro.utcluj.sd.dto.UserDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginCommand implements Command<String> {

    private final Label label;
    private final String username;
    private final String password;

    public LoginCommand(Label label, String username, String password) {
        this.label = label;
        this.username = username;
        this.password = password;
    }

    @Override
    public String execute() {
        RequestToServer myRequest = new RequestToServer();
        myRequest.getParams().put("username", username);
        myRequest.getParams().put("password", password);
        myRequest.getParams().put("command", "login");

        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(myRequest);
        System.out.println(json);

        try (Socket clientSocket = new Socket("127.0.0.1", 1997)) {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            out.println(json);
            String fromServer = in.readLine();
            UserDTO userDTO = gson.fromJson(fromServer, UserDTO.class);
            System.out.println(userDTO);
            if (userDTO == null) {
                label.setText("Invalid Credentials");
            } else if (userDTO.isAdmin()) {
                label.setText(userDTO.toString());
                // go to admin page
            } else {
                label.setText(userDTO.toString());
                //go to user page
            }
            label.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "/secondView.fxml";
    }

    @Override
    public String undo() {
        return "/secondView.fxml";
    }
}
