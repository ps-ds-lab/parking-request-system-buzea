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

import java.util.List;
import java.util.Optional;

import ro.utcluj.sd.dao.RequestDao;
import ro.utcluj.sd.dto.Dto;
import ro.utcluj.sd.entities.ParkingLot;
import ro.utcluj.sd.entities.ParkingSpace;
import ro.utcluj.sd.entities.Request;

public class AssignParkingSpotTS implements AutoCloseable, TransactionScript {
    private final long parkingLotId;
    private RequestDao requestDao = new RequestDao();

    public AssignParkingSpotTS(long parkingLotId) {
        this.parkingLotId = parkingLotId;
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
        return null;
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

    @Override
    public void close() throws Exception {
        requestDao.close();
    }
}
