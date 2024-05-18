package com.example.hotelreservations.dao;

import com.example.hotelreservations.domain.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservationDAO {

    private final Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    public void addReservation(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservations (hotel_id, room_id, user_name, check_in_date, check_out_date, is_cancelled) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, reservation.getHotelId());
            statement.setInt(2, reservation.getRoomId());
            statement.setString(3, reservation.getUserName());
            statement.setDate(4, new java.sql.Date(reservation.getCheckInDate().getTime()));
            statement.setDate(5, new java.sql.Date(reservation.getCheckOutDate().getTime()));
            statement.setBoolean(6, reservation.isCancelled());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to retrieve auto-generated reservation ID.");
                }
            }
        }
    }

    public void updateReservation(Reservation reservation) throws SQLException {
        String query = "UPDATE reservations SET hotel_id = ?, room_id = ?, user_name = ?, check_in_date = ?, check_out_date = ?, is_cancelled = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservation.getHotelId());
            statement.setInt(2, reservation.getRoomId());
            statement.setString(3, reservation.getUserName());
            statement.setDate(4, new java.sql.Date(reservation.getCheckInDate().getTime()));
            statement.setDate(5, new java.sql.Date(reservation.getCheckOutDate().getTime()));
            statement.setBoolean(6, reservation.isCancelled());
            statement.setInt(7, reservation.getId());
            statement.executeUpdate();
        }
    }

    public void deleteReservation(int reservationId) throws SQLException {
        String query = "DELETE FROM reservations WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            statement.executeUpdate();
        }
    }

    public Reservation getReservationById(int reservationId) throws SQLException {
        String query = "SELECT * FROM reservations WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Reservation(
                            resultSet.getInt("id"),
                            resultSet.getInt("hotel_id"),
                            resultSet.getInt("room_id"),
                            resultSet.getString("user_name"),
                            resultSet.getDate("check_in_date"),
                            resultSet.getDate("check_out_date"),
                            resultSet.getBoolean("is_cancelled")
                    );
                }
            }
        }
        return null;
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                reservations.add(new Reservation(
                        resultSet.getInt("id"),
                        resultSet.getInt("hotel_id"),
                        resultSet.getInt("room_id"),
                        resultSet.getString("user_name"),
                        resultSet.getDate("check_in_date"),
                        resultSet.getDate("check_out_date"),
                        resultSet.getBoolean("is_cancelled")
                ));
            }
        }
        return reservations;
    }
}
