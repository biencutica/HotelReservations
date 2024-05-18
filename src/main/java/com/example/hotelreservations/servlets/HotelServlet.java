package com.example.hotelreservations.servlets;

import com.example.hotelreservations.domain.Hotel;
import com.example.hotelreservations.service.HotelService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/hotelList")
public class HotelServlet extends HttpServlet {
    private final HotelService hotelService;

    public HotelServlet() {
        this.hotelService = new HotelService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            showAllHotels(request, response);
        } else if (action.equals("filter")) {
            filterHotelsByRadius(request, response);
        } else {

        }
    }

    private void showAllHotels(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Hotel> allHotels = hotelService.getAllHotels();
            request.setAttribute("hotels", allHotels);
            request.getRequestDispatcher("hotelList.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }

    private void filterHotelsByRadius(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            double latitude = Double.parseDouble(request.getParameter("latitude"));
            double longitude = Double.parseDouble(request.getParameter("longitude"));
            double radius = Double.parseDouble(request.getParameter("radius"));

            List<Hotel> nearbyHotels = hotelService.getNearbyHotels(latitude, longitude, radius);
            request.setAttribute("hotels", nearbyHotels);
            request.getRequestDispatcher("hotelList.jsp").forward(request, response);
        } catch (Exception e) {
        }
    }
}
