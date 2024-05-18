package com.example.hotelreservations.dao;

import com.example.hotelreservations.domain.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO {

    private Connection connection;

    public RoomDAO(Connection connection) {
        this.connection = connection;
    }

    public void addRoom(int hotelId, Room room) throws SQLException {
        try {
            if (connection.isClosed()) {
                connection = com.example.hotelreservations.repository.DBUtil.getConnection();
            }
            String query = "INSERT INTO rooms (hotel_id, roomNumber, type, price, isAvailable) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, hotelId);
                statement.setInt(2, room.getRoomNumber());
                statement.setInt(3, room.getType());
                statement.setDouble(4, room.getPrice());
                statement.setBoolean(5, room.isAvailable());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateRoom(int hotelId, Room room) throws SQLException {
        try {
            if (connection.isClosed()) {
                connection = com.example.hotelreservations.repository.DBUtil.getConnection();
            }
            String query = "UPDATE rooms SET type = ?, price = ?, isAvailable = ? WHERE hotel_id = ? AND roomNumber = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, room.getType());
                statement.setDouble(2, room.getPrice());
                statement.setBoolean(3, room.isAvailable());
                statement.setInt(4, hotelId);
                statement.setInt(5, room.getRoomNumber());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void deleteRoom(int hotelId, int roomNumber) throws SQLException {
        try {
            if (connection.isClosed()) {
                connection = com.example.hotelreservations.repository.DBUtil.getConnection();
            }
            String query = "DELETE FROM rooms WHERE hotel_id = ? AND roomNumber = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, hotelId);
                statement.setInt(2, roomNumber);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Room> getAllRoomsForHotel(int hotelId) throws SQLException {
        try {
            if (connection.isClosed()) {
                connection = com.example.hotelreservations.repository.DBUtil.getConnection();
            }
            List<Room> rooms = new ArrayList<>();
            String query = "SELECT * FROM rooms WHERE hotel_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, hotelId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int roomNumber = resultSet.getInt("roomNumber");
                        int type = resultSet.getInt("type");
                        double price = resultSet.getDouble("price");
                        boolean isAvailable = resultSet.getBoolean("isAvailable");
                        rooms.add(new Room(roomNumber, type, price, isAvailable));
                    }
                }
            }
            return rooms;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Room> getAvailableRoomsForHotel(int hotelId) throws SQLException {
        try {
            if (connection.isClosed()) {
                connection = com.example.hotelreservations.repository.DBUtil.getConnection();
            }
            List<Room> availableRooms = new ArrayList<>();
            String query = "SELECT * FROM rooms WHERE hotel_id = ? AND isAvailable = true";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, hotelId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int roomNumber = resultSet.getInt("roomNumber");
                        int type = resultSet.getInt("type");
                        double price = resultSet.getDouble("price");
                        boolean isAvailable = resultSet.getBoolean("isAvailable");
                        Room room = new Room(roomNumber, type, price, isAvailable);
                        availableRooms.add(room);
                    }
                }
            }
            return availableRooms;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }
}
