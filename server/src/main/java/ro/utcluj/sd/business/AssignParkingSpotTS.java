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

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ro.utcluj.sd.Server;
import ro.utcluj.sd.dao.RequestDao;
import ro.utcluj.sd.dto.Dto;
import ro.utcluj.sd.dto.NotificationDTO;
import ro.utcluj.sd.entities.ParkingLot;
import ro.utcluj.sd.entities.ParkingSpace;
import ro.utcluj.sd.entities.Request;

public class AssignParkingSpotTS implements TransactionScript {
    private final long parkingLotId;
    private final Gson gson;
    private RequestDao requestDao = new RequestDao();

    public AssignParkingSpotTS(long parkingLotId) {
        this.parkingLotId = parkingLotId;
        this.gson = new GsonBuilder().create();
    }

    public Dto execute() {
        List<Request> requests = requestDao.findByParkingLotId(parkingLotId);
        Optional<Request> unassignedRequest = getUnassignedRequest(requests);

        unassignedRequest
                .ifPresent(request ->
                        findLot(request)
                                .ifPresent(lot ->
                                        findFirstFreeParkingSpot(lot)
                                                .ifPresent(space -> {
                                                    request.setParkingSpace(space);
                                                    space.setFree(false);
                                                    requestDao.save(request);
                                                })));

        Server.getInstance()
                .getObservers()
                .forEach(this::sendNotification);

        return null;
    }

    private void sendNotification(Socket socket) {
        if (socket.isClosed()) {
            return;
        }
        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            NotificationDTO notification = new NotificationDTO();
            notification.setMessage("Alert! Alert! Alert! Bi-doo! Bi-doo");
            String json = gson.toJson(notification);
            out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Optional<Request> getUnassignedRequest(List<Request> requests) {
        return requests
                .stream()
                .filter(request -> request.getParkingSpace() == null) //not assigned yet
                .findFirst();
    }

    private Optional<ParkingSpace> findFirstFreeParkingSpot(ParkingLot lot) {
        return lot.getParkingSpaces()
                .stream()
                .filter(ParkingSpace::isFree)
                .findAny();
    }

    private Optional<ParkingLot> findLot(Request request) {
        return request.getParkingLots()
                .stream()
                .filter(lot -> lot.getId() == parkingLotId)
                .findAny();
    }
}
