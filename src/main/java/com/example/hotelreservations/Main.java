package com.example.hotelreservations;

import com.example.hotelreservations.dao.HotelDAO;
import com.example.hotelreservations.domain.Hotel;
import com.example.hotelreservations.service.HotelService;
import com.example.hotelreservations.repository.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Create a connection
        Connection connection = DBUtil.getConnection();

        // Create an instance of HotelDAO
        HotelDAO hotelDAO = new HotelDAO(connection);

        // Create an instance of HotelService with the HotelDAO
        HotelService hotelService = new HotelService(hotelDAO);

        // Test getAllHotels method
        testGetAllHotels(hotelService);

        // Test getNearbyHotels method
        testGetNearbyHotels(hotelService, 46.77, 23.59, 5.0); // Example coordinates and radius
    }

    private static void testGetAllHotels(HotelService hotelService) {
        System.out.println("Testing getAllHotels method...");

        try {
            // Retrieve all hotels
            List<Hotel> hotels = hotelService.getAllHotels();

            // Print hotel information
            for (Hotel hotel : hotels) {
                System.out.println("Hotel ID: " + hotel.getId());
                System.out.println("Hotel Name: " + hotel.getName());
                System.out.println("Latitude: " + hotel.getLatitude());
                System.out.println("Longitude: " + hotel.getLongitude());
                System.out.println();
            }

            System.out.println("Total hotels retrieved: " + hotels.size());
        } catch (Exception e) {
            System.err.println("Error occurred while fetching hotels: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testGetNearbyHotels(HotelService hotelService, double latitude, double longitude, double radius) {
        System.out.println("Testing getNearbyHotels method...");

        try {
            // Retrieve nearby hotels
            List<Hotel> nearbyHotels = hotelService.getNearbyHotels(latitude, longitude, radius);

            // Print nearby hotel information
            for (Hotel hotel : nearbyHotels) {
                System.out.println("Hotel ID: " + hotel.getId());
                System.out.println("Hotel Name: " + hotel.getName());
                System.out.println("Latitude: " + hotel.getLatitude());
                System.out.println("Longitude: " + hotel.getLongitude());
                System.out.println();
            }

            System.out.println("Total nearby hotels retrieved: " + nearbyHotels.size());
        } catch (Exception e) {
            System.err.println("Error occurred while fetching nearby hotels: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
