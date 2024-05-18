package com.example.hotelreservations;
import com.example.hotelreservations.domain.Hotel;
import com.example.hotelreservations.domain.Room;
import com.example.hotelreservations.repository.DBInitializer;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

            DBInitializer.hotel_stat(hotels, hotelStatement, roomStatement);
        } catch (FileNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
