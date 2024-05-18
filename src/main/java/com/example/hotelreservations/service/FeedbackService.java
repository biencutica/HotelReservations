package com.example.hotelreservations.service;

import com.example.hotelreservations.dao.FeedbackDAO;
import com.example.hotelreservations.domain.Feedback;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class FeedbackService {

    private final FeedbackDAO feedbackDAO;

    public FeedbackService() {
        try (Connection connection = com.example.hotelreservations.repository.DBUtil.getConnection()) {
            this.feedbackDAO = new FeedbackDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing FeedbackService", e);
        }
    }

    public void addFeedback(Feedback feedback) {
        try {
            feedbackDAO.addFeedback(feedback);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding feedback", e);
        }
    }

    public List<Feedback> getFeedbackByHotel(int hotelId) {
        try {
            return feedbackDAO.getFeedbackByHotel(hotelId);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving feedback for hotel ID: " + hotelId, e);
        }
    }

    public void updateFeedback(Feedback feedback) {
        try {
            feedbackDAO.updateFeedback(feedback);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating feedback with ID: " + feedback.getId(), e);
        }
    }

    public void deleteFeedback(int feedbackId) {
        try {
            feedbackDAO.deleteFeedback(feedbackId);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting feedback with ID: " + feedbackId, e);
        }
    }
}
