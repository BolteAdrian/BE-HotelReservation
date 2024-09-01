package com.example.hotelreservation.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 * Entity class representing a room in a hotel.
 * This class maps to the "rooms" table in the database and contains details
 * about the room including its number, type, price, availability, and associated hotel.
 */
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_number", nullable = false)
    private int roomNumber;

    /**
     * Type of the room.
     * 1: Single
     * 2: Double
     * 3: Suite
     * 4: Matrimonial
     */
    @Column(nullable = false)
    private int type;

    @Column(nullable = false)
    private double price;

    @JsonProperty("isAvailable")
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    @JsonBackReference
    private Hotel hotel;

    // Getters and setters

    /**
     * Gets the unique identifier of the room.
     *
     * @return the ID of the room.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the room.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the room number.
     *
     * @return the room number.
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * Sets the room number.
     *
     * @param roomNumber the room number to set.
     */
    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    /**
     * Gets the type of the room.
     *
     * @return the type of the room.
     */
    public int getType() {
        return type;
    }

    /**
     * Sets the type of the room.
     *
     * @param type the type of the room to set.
     *             1: Single
     *             2: Double
     *             3: Suite
     *             4: Matrimonial
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Gets the price of the room.
     *
     * @return the price of the room.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the room.
     *
     * @param price the price to set.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Checks if the room is available.
     *
     * @return true if the room is available, false otherwise.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the availability status of the room.
     *
     * @param available true if the room is available, false otherwise.
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Gets the hotel associated with this room.
     *
     * @return the {@link Hotel} object associated with this room.
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Sets the hotel associated with this room.
     *
     * @param hotel the {@link Hotel} to set.
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
