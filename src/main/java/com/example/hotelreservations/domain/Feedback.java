package com.example.hotelreservations.domain;

public class Feedback {
    private int id;
    private int hotelId;
    private String userName;
    private String comments;
    private int rating;

    public Feedback(int id, int hotelId, String userName, String comments, int rating) {
        this.id = id;
        this.hotelId = hotelId;
        this.userName = userName;
        this.comments = comments;
        this.rating = rating;
    }

    public Feedback(int hotelId, String userName, String comments, int rating) {
        this(0, hotelId, userName, comments, rating);
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
