package com.example.hotelreservation.modelDto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) that represents the details required to make a reservation.
 * This class encapsulates the necessary information for booking a room.
 */
public class ReservationDto {

    // The ID of the user making the reservation
    private Long userId;

    // The ID of the room being reserved
    private Long roomId;

    // The check-in date and time for the reservation
    private LocalDateTime checkIn;

    // The check-out date and time for the reservation
    private LocalDateTime checkOut;

    /**
     * Gets the ID of the user making the reservation.
     *
     * @return the user ID as a {@link Long}.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user making the reservation.
     *
     * @param userId the user ID to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the ID of the room being reserved.
     *
     * @return the room ID as a {@link Long}.
     */
    public Long getRoomId() {
        return roomId;
    }

    /**
     * Sets the ID of the room being reserved.
     *
     * @param roomId the room ID to set.
     */
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    /**
     * Gets the check-in date and time for the reservation.
     *
     * @return the check-in {@link LocalDateTime}.
     */
    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    /**
     * Sets the check-in date and time for the reservation.
     *
     * @param checkIn the check-in {@link LocalDateTime} to set.
     */
    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    /**
     * Gets the check-out date and time for the reservation.
     *
     * @return the check-out {@link LocalDateTime}.
     */
    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    /**
     * Sets the check-out date and time for the reservation.
     *
     * @param checkOut the check-out {@link LocalDateTime} to set.
     */
    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }
}
