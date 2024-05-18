package com.example.hotelreservations.service;

import com.example.hotelreservations.dao.ReservationDAO;
import com.example.hotelreservations.domain.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ReservationService {

    private final ReservationDAO reservationDAO;

    public ReservationService() {
        try (Connection connection = com.example.hotelreservations.repository.DBUtil.getConnection()) {
            this.reservationDAO = new ReservationDAO(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing ReservationService", e);
        }
    }

    public void addReservation(Reservation reservation) {
        try {
            reservationDAO.addReservation(reservation);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding reservation for user: " + reservation.getUserName(), e);
        }
    }

    public void updateReservation(Reservation reservation) {
        try {
            reservationDAO.updateReservation(reservation);
        } catch (SQLException e) {
            throw new RuntimeException("Error updating reservation with ID: " + reservation.getId(), e);
        }
    }

    public void deleteReservation(int reservationId) {
        try {
            reservationDAO.deleteReservation(reservationId);
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting reservation with ID: " + reservationId, e);
        }
    }

    public Reservation getReservationById(int reservationId) {
        try {
            return reservationDAO.getReservationById(reservationId);
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching reservation with ID: " + reservationId, e);
        }
    }

    public List<Reservation> getAllReservations() {
        try {
            return reservationDAO.getAllReservations();
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all reservations", e);
        }
    }
}
