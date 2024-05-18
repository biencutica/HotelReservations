package com.example.hotelreservations.servlets;

import java.io.*;
import java.util.List;

import com.example.hotelreservations.domain.Hotel;
import com.example.hotelreservations.service.HotelService; // Import the HotelService class
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    private HotelService hotelService;

    @Override
    public void init() throws ServletException {
        super.init();
        hotelService = new HotelService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String location = request.getParameter("location");

        try {
            List<Hotel> hotels = hotelService.searchHotels(location);

            request.setAttribute("hotels", hotels);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("hotelList.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
