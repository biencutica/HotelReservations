package com.example.hotelreservations.service;

import com.example.hotelreservations.dao.RoomDAO;
import com.example.hotelreservations.domain.Room;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoomService {
    private final RoomDAO roomDAO;

    public RoomService() {
        try (Connection connection = com.example.hotelreservations.repository.DBUtil.getConnection()) {
            this.roomDAO = new RoomDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing RoomService", e);
        }
    }

    public void addRoom(int hotelId, Room room) {
        try {
            roomDAO.addRoom(hotelId, room);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding room to hotel with ID: " + hotelId, e);
        }
    }

    public void updateRoom(int hotelId, Room room) {
        try {
            roomDAO.updateRoom(hotelId, room);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating room for hotel with ID: " + hotelId, e);
        }
    }

    public void deleteRoom(int hotelId, int roomNumber) {
        try {
            roomDAO.deleteRoom(hotelId, roomNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting room for hotel with ID: " + hotelId, e);
        }
    }

    public List<Room> getRoomsForHotel(int hotelId) {
        try {
            return roomDAO.getAllRoomsForHotel(hotelId);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching rooms for hotel with ID: " + hotelId, e);
        }
    }
}
