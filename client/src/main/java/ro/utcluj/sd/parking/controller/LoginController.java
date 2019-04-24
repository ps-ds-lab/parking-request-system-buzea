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

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ro.utcluj.sd.dto.UserDTO;

public class LoginController
{
    @FXML
    public PasswordField password;
    @FXML
    public TextField username;
    @FXML
    public Label label;

    public void login(ActionEvent actionEvent)
    {
        UserDTO vlad = new UserDTO();
        vlad.setAdmin(true);
        vlad.setUsername("I <3 Software Design!");

        label.setText(vlad.toString());
        label.setVisible(!label.isVisible());
    }
}
