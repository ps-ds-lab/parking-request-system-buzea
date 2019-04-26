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
package ro.utcluj.sd.business;

import ro.utcluj.sd.dao.UserDao;
import ro.utcluj.sd.dto.UserDTO;

public class LoginTS implements AutoCloseable, TransactionScript {
    private final String username;
    private final String password;
    private UserDao userDao = new UserDao();

    public LoginTS(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO execute() {
        if (username.equals("admin") && password.equals("admin")) {
            UserDTO dto = new UserDTO();
            dto.setUsername("Timotei Dolean");
            dto.setAdmin(true);
            return dto;
        }

        return userDao.findByUsername(username)
                .filter(user -> user.getPassword().equals(password))
                .map(user -> toDTO())
                .orElse(null);
    }

    private UserDTO toDTO() {
        UserDTO dto = new UserDTO();
        dto.setUsername(username);
        dto.setAdmin(false);
        return dto;
    }

    @Override
    public void close() {
        userDao.close();
    }
}
