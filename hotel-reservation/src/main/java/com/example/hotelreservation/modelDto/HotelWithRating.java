package com.example.hotelreservation.modelDto;

import com.example.hotelreservation.model.Hotel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Transfer Object (DTO) that represents a hotel along with its average rating.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HotelWithRating {

    // The hotel entity containing details about the hotel
    private SimpleHotelDto hotel;

    // The average rating of the hotel based on user feedback
    private double averageRating;

    /**
     * Constructs a new instance of HotelWithRating with the specified hotel and average rating.
     */
    public HotelWithRating(SimpleHotelDto hotel, double averageRating) {
        this.hotel = hotel;
        this.averageRating = averageRating;
    }

    // Getters and Setters

    /**
     * Gets the hotel entity containing details about the hotel.
     * Exclude feedbacks and rooms to simplify the JSON structure.
     */
    @JsonProperty("hotel")
    public SimpleHotelDto getHotel() {
        return hotel;
    }

    public void setHotel(SimpleHotelDto hotel) {
        this.hotel = hotel;
    }

    /**
     * Gets the average rating of the hotel.
     */
    @JsonProperty("averageRating")
    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }
}
