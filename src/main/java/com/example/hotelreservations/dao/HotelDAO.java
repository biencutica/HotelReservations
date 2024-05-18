package com.example.hotelreservations.dao;

import com.example.hotelreservations.domain.Hotel;
import com.example.hotelreservations.domain.Room;
import com.example.hotelreservations.repository.DBInitializer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

    private final Connection connection;

    public HotelDAO(Connection connection) {
        this.connection = connection;
    }

    public void addHotel(Hotel hotel) throws SQLException {
        String insertHotel = "INSERT INTO hotels (name, latitude, longitude) VALUES (?, ?, ?)";
        String insertRoom = "INSERT INTO rooms (hotel_id, roomNumber, type, price, isAvailable) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement hotelStatement = connection.prepareStatement(insertHotel, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement roomStatement = connection.prepareStatement(insertRoom)) {

            connection.setAutoCommit(false);

            try {
                hotelStatement.setString(1, hotel.getName());
                hotelStatement.setDouble(2, hotel.getLatitude());
                hotelStatement.setDouble(3, hotel.getLongitude());
                hotelStatement.executeUpdate();

                try (ResultSet generatedKeys = hotelStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        hotel.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Failed to retrieve auto-generated hotel ID.");
                    }
                }

                DBInitializer.room_stat(roomStatement, hotel);

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }


    public void updateHotel(Hotel hotel) throws SQLException {
        String updateHotel = "UPDATE hotels SET name = ?, latitude = ?, longitude = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(updateHotel)) {
            statement.setString(1, hotel.getName());
            statement.setDouble(2, hotel.getLatitude());
            statement.setDouble(3, hotel.getLongitude());
            statement.setInt(4, hotel.getId());
            statement.executeUpdate();
        }
    }

    public void deleteHotel(int hotelId) throws SQLException {
        String deleteRooms = "DELETE FROM rooms WHERE hotel_id = ?";
        String deleteHotel = "DELETE FROM hotels WHERE id = ?";
        try (PreparedStatement deleteRoomsStatement = connection.prepareStatement(deleteRooms);
             PreparedStatement deleteHotelStatement = connection.prepareStatement(deleteHotel)) {
            connection.setAutoCommit(false);
            try {
                deleteRoomsStatement.setInt(1, hotelId);
                deleteRoomsStatement.executeUpdate();

                deleteHotelStatement.setInt(1, hotelId);
                deleteHotelStatement.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    public Hotel getHotelById(int hotelId) throws SQLException {
        Hotel hotel = null;
        String query = "SELECT * FROM hotels WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("name");
                    double latitude = resultSet.getDouble("latitude");
                    double longitude = resultSet.getDouble("longitude");
                    hotel = new Hotel(hotelId, name, latitude, longitude);
                }
            }
        }
        return hotel;
    }

    public List<Hotel> getAllHotels() throws SQLException {
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT * FROM hotels";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double latitude = resultSet.getDouble("latitude");
                double longitude = resultSet.getDouble("longitude");

                List<Room> rooms = getRoomsForHotel(id);

                hotels.add(new Hotel(id, name, latitude, longitude, rooms));
            }
        }
        return hotels;
    }

    private List<Room> getRoomsForHotel(int hotelId) throws SQLException {
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
    }

}
