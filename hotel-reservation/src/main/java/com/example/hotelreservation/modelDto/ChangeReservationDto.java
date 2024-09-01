package com.example.hotelreservation.modelDto;

/**
 * Data Transfer Object (DTO) for changing a reservation.
 * This class encapsulates the details required to modify an existing reservation,
 * including the user's ID, the hotel's ID, and the new room's ID.
 */
public class ChangeReservationDto {

    // Unique identifier of the user requesting the change
    private Long userId;

    // Unique identifier of the hotel where the reservation is made
    private Long hotelId;

    // Unique identifier of the new room to which the reservation will be changed
    private Long newRoomId;

    // Getters and Setters

    /**
     * Gets the unique identifier of the user requesting the reservation change.
     *
     * @return the user ID.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the user requesting the reservation change.
     *
     * @param userId the user ID to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the unique identifier of the hotel where the reservation is made.
     *
     * @return the hotel ID.
     */
    public Long getHotelId() {
        return hotelId;
    }

    /**
     * Sets the unique identifier of the hotel where the reservation is made.
     *
     * @param hotelId the hotel ID to set.
     */
    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    /**
     * Gets the unique identifier of the new room to which the reservation will be changed.
     *
     * @return the new room ID.
     */
    public Long getNewRoomId() {
        return newRoomId;
    }

    /**
     * Sets the unique identifier of the new room to which the reservation will be changed.
     *
     * @param newRoomId the new room ID to set.
     */
    public void setNewRoomId(Long newRoomId) {
        this.newRoomId = newRoomId;
    }
}
