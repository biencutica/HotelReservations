package com.example.hotelreservations.dao;

import com.example.hotelreservations.domain.Feedback;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {

    private final Connection connection;

    public FeedbackDAO(Connection connection) {
        this.connection = connection;
    }

    public void addFeedback(Feedback feedback) throws SQLException {
        String query = "INSERT INTO feedback (hotel_id, user_name, comments, rating) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, feedback.getHotelId());
            statement.setString(2, feedback.getUserName());
            statement.setString(3, feedback.getComments());
            statement.setInt(4, feedback.getRating());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                feedback.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Failed to retrieve auto-generated feedback ID.");
            }
        }
    }

    public List<Feedback> getFeedbackByHotel(int hotelId) throws SQLException {
        List<Feedback> feedbackList = new ArrayList<>();
        String query = "SELECT * FROM feedback WHERE hotel_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, hotelId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String userName = resultSet.getString("user_name");
                    String comments = resultSet.getString("comments");
                    int rating = resultSet.getInt("rating");
                    feedbackList.add(new Feedback(id, hotelId, userName, comments, rating));
                }
            }
        }
        return feedbackList;
    }

    public void updateFeedback(Feedback feedback) throws SQLException {
        String query = "UPDATE feedback SET comments = ?, rating = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, feedback.getComments());
            statement.setInt(2, feedback.getRating());
            statement.setInt(3, feedback.getId());
            statement.executeUpdate();
        }
    }

    public void deleteFeedback(int feedbackId) throws SQLException {
        String query = "DELETE FROM feedback WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, feedbackId);
            statement.executeUpdate();
        }
    }
}
