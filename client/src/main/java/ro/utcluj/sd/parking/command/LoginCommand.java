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

import javafx.scene.control.Label;
import ro.utcluj.sd.dto.UserDTO;

public class LoginCommand implements Command<String> {

    private final Label label;

    public LoginCommand(Label label) {
        this.label = label;
    }

    @Override
    public String execute() {
        UserDTO vlad = new UserDTO();
        vlad.setAdmin(true);
        vlad.setUsername("I <3 Software Design!");

        label.setText(vlad.toString());
        label.setVisible(!label.isVisible());
        return "/secondView.fxml";
    }

    @Override
    public String undo() {
        return "/secondView.fxml";
    }
}
