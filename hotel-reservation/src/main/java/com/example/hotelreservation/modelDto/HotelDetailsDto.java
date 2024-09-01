package com.example.hotelreservation.modelDto;

import com.example.hotelreservation.model.Room;
import com.example.hotelreservation.model.Feedback;
import java.util.List;

/**
 * Data Transfer Object (DTO) that encapsulates the combined details of a hotel.
 * This includes both the list of rooms available in the hotel, the feedback provided by users,
 * and a flag indicating if the current user has any reservations at the hotel.
 */
public class HotelDetailsDto {

    /**
     * List of rooms available in the hotel.
     * Each {@link Room} object contains details about a specific room.
     */
    private List<Room> rooms;

    /**
     * List of feedbacks provided by users for the hotel.
     * Each {@link FeedbackDto} object contains user feedback and ratings.
     */
    private List<FeedbackDto> feedbacks;

    /**
     * Indicates whether the current user has a reservation at the hotel.
     * This flag is used to quickly determine if the user has an existing booking.
     */
    private boolean hasReservation;

    // Getters and Setters

    /**
     * Gets the list of rooms available in the hotel.
     *
     * @return a list of {@link Room} objects representing the available rooms.
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms available in the hotel.
     *
     * @param rooms a list of {@link Room} objects representing the available rooms.
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Gets the list of feedbacks provided by users for the hotel.
     *
     * @return a list of {@link FeedbackDto} objects representing the user feedback.
     */
    public List<FeedbackDto> getFeedbacks() {
        return feedbacks;
    }

    /**
     * Sets the list of feedbacks provided by users for the hotel.
     *
     * @param feedbacks a list of {@link FeedbackDto} objects representing the user feedback.
     */
    public void setFeedbacks(List<FeedbackDto> feedbacks) {
        this.feedbacks = feedbacks;
    }

    /**
     * Gets the flag indicating if the current user has a reservation at the hotel.
     *
     * @return {@code true} if the user has a reservation; {@code false} otherwise.
     */
    public boolean isHasReservation() {
        return hasReservation;
    }

    /**
     * Sets the flag indicating if the current user has a reservation at the hotel.
     *
     * @param hasReservation {@code true} if the user has a reservation; {@code false} otherwise.
     */
    public void setHasReservation(boolean hasReservation) {
        this.hasReservation = hasReservation;
    }
}
