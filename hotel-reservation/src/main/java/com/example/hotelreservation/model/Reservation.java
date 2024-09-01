package com.example.hotelreservation.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity class representing a reservation made by a user for a specific room.
 * This class maps to the "reservations" table in the database and contains details
 * about the reservation including user ID, room ID, check-in, and check-out times.
 */
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time", nullable = false)
    private LocalDateTime checkOutTime;

    @ManyToOne
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private Room room;

    // Getters and setters

    /**
     * Gets the unique identifier of the reservation.
     *
     * @return the ID of the reservation.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the reservation.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the ID of the user who made the reservation.
     *
     * @return the user ID.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who made the reservation.
     *
     * @param userId the user ID to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the room reserved.
     *
     * @return the room ID.
     */
    public Long getRoomId() {
        return roomId;
    }

    /**
     * Sets the ID of the room reserved.
     *
     * @param roomId the room ID to set.
     */
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    /**
     * Gets the check-in time of the reservation.
     *
     * @return the check-in time.
     */
    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    /**
     * Sets the check-in time of the reservation.
     *
     * @param checkInTime the check-in time to set.
     */
    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    /**
     * Gets the check-out time of the reservation.
     *
     * @return the check-out time.
     */
    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    /**
     * Sets the check-out time of the reservation.
     *
     * @param checkOutTime the check-out time to set.
     */
    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    /**
     * Gets the room associated with this reservation.
     *
     * @return the {@link Room} object associated with this reservation.
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room associated with this reservation.
     *
     * @param room the {@link Room} to set.
     */
    public void setRoom(Room room) {
        this.room = room;
    }
}
