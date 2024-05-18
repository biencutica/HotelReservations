package com.example.hotelreservations.domain;

import java.util.Date;

public class Reservation {
    private int id;
    private int hotelId;
    private int roomId;
    private String userName;
    private Date checkInDate;
    private Date checkOutDate;
    private boolean isCancelled;

    // Constructor
    public Reservation(int id, int hotelId, int roomId, String userName, Date checkInDate, Date checkOutDate, boolean isCancelled) {
        this.id = id;
        this.hotelId = hotelId;
        this.roomId = roomId;
        this.userName = userName;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.isCancelled = isCancelled;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}

