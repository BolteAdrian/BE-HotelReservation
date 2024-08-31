package com.example.hotelreservation.modelDto;

import com.example.hotelreservation.model.Room;
import com.example.hotelreservation.model.Feedback;
import java.util.List;

/**
 * DTO class that represents the combined data of rooms and feedback for a hotel.
 */
public class HotelDetailsDto {

    private List<Room> rooms;
    private List<Feedback> feedbacks;

    // Getters and Setters
    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
