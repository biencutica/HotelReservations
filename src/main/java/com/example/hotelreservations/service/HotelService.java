package com.example.hotelreservations.service;

import com.example.hotelreservations.dao.FeedbackDAO;
import com.example.hotelreservations.dao.HotelDAO;
import com.example.hotelreservations.dao.RoomDAO;
import com.example.hotelreservations.domain.Feedback;
import com.example.hotelreservations.domain.Hotel;
import com.example.hotelreservations.domain.Room;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HotelService {
    private final HotelDAO hotelDAO;
    private final FeedbackDAO feedbackDAO;

    public HotelService() {
        try (Connection connection = com.example.hotelreservations.repository.DBUtil.getConnection()) {
            this.hotelDAO = new HotelDAO(connection);
            this.feedbackDAO = new FeedbackDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing HotelService", e);
        }
    }

    public HotelService(HotelDAO hotelDAO) {
        try (Connection connection = com.example.hotelreservations.repository.DBUtil.getConnection()) {
            this.feedbackDAO = new FeedbackDAO(connection);
            this.hotelDAO = hotelDAO;
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing HotelService", e);
        }
    }

    public void addHotel(String name, double latitude, double longitude) {
        try {
            Hotel hotel = new Hotel(0, name, latitude, longitude, new ArrayList<>());
            hotelDAO.addHotel(hotel);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding hotel: " + name, e);
        }
    }

    public void submitFeedback(Feedback feedback) {
        try {
            feedbackDAO.addFeedback(feedback);
        } catch (SQLException e) {
            throw new RuntimeException("Error submitting feedback", e);
        }
    }

    public void deleteFeedback(int feedbackId) {
        try {
            feedbackDAO.deleteFeedback(feedbackId);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting feedback with ID: " + feedbackId, e);
        }
    }

    public List<Feedback> getFeedbackByHotel(int hotelId) {
        try {
            return feedbackDAO.getFeedbackByHotel(hotelId);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching feedbacks for hotel: " + hotelId, e);
        }
    }

    public void updateFeedback(Feedback feedback) {
        try {
            feedbackDAO.updateFeedback(feedback);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating feedback: " + feedback.getId(), e);
        }
    }


    public void updateHotel(int id, String name, double latitude, double longitude) {
        try {
            Hotel hotel = new Hotel(id, name, latitude, longitude, new ArrayList<>());
            hotelDAO.updateHotel(hotel);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating hotel with ID: " + id, e);
        }
    }

    public void deleteHotel(int hotelId) {
        try {
            hotelDAO.deleteHotel(hotelId);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting hotel with ID: " + hotelId, e);
        }
    }

    public List<Hotel> getAllHotels() {
        try {
            return hotelDAO.getAllHotels();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all hotels", e);
        }
    }

    public Hotel getHotelById(int hotelId) {
        try {
            return hotelDAO.getHotelById(hotelId);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching hotel with ID: " + hotelId, e);
        }
    }

    public List<Hotel> searchHotels(String location) {
        return null;
    }

    public List<Hotel> getNearbyHotels(double latitude, double longitude, double radius) throws SQLException {
        return hotelDAO.getNearbyHotels(latitude, longitude, radius);
    }
}
