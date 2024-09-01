package com.example.hotelreservation.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

/**
 * Entity class representing a hotel.
 * This class maps to the "hotels" table in the database and contains details about the hotel,
 * including its name, location, and associated rooms and feedbacks.
 */
@Entity
@Table(name = "hotels")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Room> rooms;

    @OneToMany(mappedBy = "hotel")
    private List<Feedback> feedbacks;

    // Getters and setters

    /**
     * Gets the unique identifier of the hotel.
     *
     * @return the ID of the hotel.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the hotel.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name of the hotel.
     *
     * @return the name of the hotel.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the hotel.
     *
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the latitude of the hotel's location.
     *
     * @return the latitude.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Sets the latitude of the hotel's location.
     *
     * @param latitude the latitude to set.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Gets the longitude of the hotel's location.
     *
     * @return the longitude.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Sets the longitude of the hotel's location.
     *
     * @param longitude the longitude to set.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Gets the list of rooms associated with the hotel.
     *
     * @return a list of {@link Room} objects.
     */
    public List<Room> getRooms() {
        return rooms;
    }

    /**
     * Sets the list of rooms associated with the hotel.
     *
     * @param rooms the list of {@link Room} objects to set.
     */
    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Gets the list of feedbacks associated with the hotel.
     *
     * @return a list of {@link Feedback} objects.
     */
    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    /**
     * Sets the list of feedbacks associated with the hotel.
     *
     * @param feedbacks the list of {@link Feedback} objects to set.
     */
    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
}
