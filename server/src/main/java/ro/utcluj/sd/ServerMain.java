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
package ro.utcluj.sd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ro.utcluj.sd.business.LoginTS;
import ro.utcluj.sd.dto.UserDTO;
import ro.utcluj.sd.entities.Car;
import ro.utcluj.sd.entities.ParkingLot;
import ro.utcluj.sd.entities.ParkingSpace;
import ro.utcluj.sd.entities.Request;
import ro.utcluj.sd.entities.User;
import ro.utcluj.sd.dao.util.HibernateUtil;

public class ServerMain {

    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();

        demoGson(gson);
        setupData();

        Server.getInstance().start();
    }

    private static void demoGson(Gson gson) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("admin");
        userDTO.setAdmin(true);
        String json = gson.toJson(userDTO);
        System.out.println(json);

        String secondUserJson = "{\"username\":\"vlad\",\"admin\":false}";
        UserDTO secondUserDTO = gson.fromJson(secondUserJson, UserDTO.class);
        System.out.println(secondUserDTO);
    }

    private static void setupData() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        User u1 = new User();
        u1.setId(1L);
        session.save(u1);
        u1.setUsername("Vlad");
        u1.setPassword("Vlad");

        Car c1 = new Car();
        c1.setId(1L);
        c1.setVin("CJ97MAD");
        c1.setUser(u1);
        u1.getCars().add(c1);

        session.save(c1);
        session.flush();

        ParkingLot p1 = createLotWithFreeSpace(session, 1, 1);
        ParkingLot p2 = createLotWithFreeSpace(session, 3, 3);

        Request request = new Request();
        request.setId(1L);
        request.setCar(c1);
        request.getParkingLots().add(p1);
        request.getParkingLots().add(p2);
        request.setSubmissionTime(new Date());

        session.save(request);

        transaction.commit();
    }

    private static ParkingLot createLotWithFreeSpace(Session session, long id1, long id2) {
        ParkingLot p1 = new ParkingLot();
        p1.setId(id1);
        session.save(p1);

        ParkingSpace space1 = new ParkingSpace();
        space1.setFree(true);
        space1.setId(id2);
        space1.setParkingLot(p1);

        p1.getParkingSpaces().add(space1);
        session.save(space1);

        return p1;
    }
}
