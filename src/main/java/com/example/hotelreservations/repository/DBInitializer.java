package com.example.hotelreservations.repository;

import com.example.hotelreservations.domain.Hotel;
import com.example.hotelreservations.domain.Room;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class DBInitializer {
    private final String url;
    private final String username;
    private final String password;

    public DBInitializer() {
        Properties properties = new Properties();
        try (InputStream inputStream = DBInitializer.class.getClassLoader().getResourceAsStream("dbinfo.properties")) {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error loading database properties", e);
        }

        url = properties.getProperty("db.url");
        username = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
    }

    public void initialize() {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Gson gson = new Gson();
            try (FileReader reader = new FileReader("src/main/resources/hotels.json")) {
                Hotel[] hotels = gson.fromJson(reader, Hotel[].class);

                String insertHotel = "INSERT INTO hotels (id, name, latitude, longitude) VALUES (?, ?, ?, ?)";
                String insertRoom = "INSERT INTO rooms (hotel_id, roomNumber, type, price, isAvailable) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement hotelStatement = connection.prepareStatement(insertHotel);
                     PreparedStatement roomStatement = connection.prepareStatement(insertRoom)) {
                    hotel_stat(hotels, hotelStatement, roomStatement);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void hotel_stat(Hotel[] hotels, PreparedStatement hotelStatement, PreparedStatement roomStatement) throws SQLException {
        for (Hotel hotel : hotels) {
            hotelStatement.setInt(1, hotel.getId());
            hotelStatement.setString(2, hotel.getName());
            hotelStatement.setDouble(3, hotel.getLatitude());
            hotelStatement.setDouble(4, hotel.getLongitude());
            hotelStatement.executeUpdate();

            room_stat(roomStatement, hotel);
        }
    }

    public static void room_stat(PreparedStatement roomStatement, Hotel hotel) throws SQLException {
        for (Room room : hotel.getRooms()) {
            roomStatement.setInt(1, hotel.getId());
            roomStatement.setInt(2, room.getRoomNumber());
            roomStatement.setInt(3, room.getType());
            roomStatement.setDouble(4, room.getPrice());
            roomStatement.setBoolean(5, room.isAvailable());
            roomStatement.executeUpdate();
        }
    }
}
