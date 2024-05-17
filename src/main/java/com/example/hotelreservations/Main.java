package com.example.hotelreservations;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.annotation.processing.Filer;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            Properties properties = new Properties();
            try (InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("dbinfo.properties")) {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");
            Connection connection = DriverManager.getConnection(url, username, password);

            Gson gson = new Gson();
            FileReader reader = new FileReader("src/main/resources/hotels.json");
            Hotel[] hotels = gson.fromJson(reader, Hotel[].class); // telling gson to parse the json data into an array of hotel objects

            String insertHotel = "INSERT INTO hotels (id, name, latitude, longitude) VALUES (?, ?, ?, ?)";
            String insertRoom = "INSERT INTO rooms (hotel_id, roomNumber, type, price, isAvailable) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement hotelStatement = connection.prepareStatement(insertHotel);
            PreparedStatement roomStatement = connection.prepareStatement(insertRoom);

            for (Hotel hotel : hotels) {
                hotelStatement.setInt(1, hotel.getId());
                hotelStatement.setString(2, hotel.getName());
                hotelStatement.setDouble(3, hotel.getLatitude());
                hotelStatement.setDouble(4, hotel.getLongitude());
                hotelStatement.executeUpdate();

                for (Room room : hotel.getRooms()) {
                    roomStatement.setInt(1, hotel.getId());
                    roomStatement.setInt(2, room.getRoomNumber());
                    roomStatement.setInt(3, room.getType());
                    roomStatement.setDouble(4, room.getPrice());
                    roomStatement.setBoolean(5, room.isAvailable());
                    roomStatement.executeUpdate();
                }
            }
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
