package com.example.hotelreservation.service;

import com.example.hotelreservation.model.Reservation;
import com.example.hotelreservation.model.Room;
import com.example.hotelreservation.repository.ReservationRepository;
import com.example.hotelreservation.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing reservations, including booking, changing, retrieving, and canceling reservations.
 */
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    /**
     * Constructor for ReservationService.
     *
     * @param reservationRepository the repository used to manage reservation data.
     * @param roomRepository the repository used to manage room data.
     */
    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * Books a room for a user for a specified time period.
     *
     * @param userId the ID of the user making the reservation.
     * @param roomId the ID of the room to be booked.
     * @param checkIn the check-in date and time.
     * @param checkOut the check-out date and time.
     * @return the created {@link Reservation} object.
     * @throws IllegalStateException if the room is not available.
     */
    public Reservation bookRoom(Long userId, Long roomId, LocalDateTime checkIn, LocalDateTime checkOut) {
        // Validate that the room is not booked during the specified period
        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(roomId, checkIn, checkOut);

        if (conflictingReservations.isEmpty()) {
            Reservation reservation = new Reservation();
            reservation.setUserId(userId);
            reservation.setRoomId(roomId);
            reservation.setCheckInTime(checkIn);
            reservation.setCheckOutTime(checkOut);

            return reservationRepository.save(reservation);
        } else {
            throw new IllegalStateException("Room is not available during the selected dates");
        }
    }

    /**
     * Checks if a specific user has any reservations at a given hotel.
     *
     * This method queries the reservation repository to determine if there are any reservations
     * associated with the provided user ID and hotel ID. It returns a boolean indicating
     * whether the user has an existing reservation at the specified hotel.
     *
     * @param userId the ID of the user whose reservation status is being checked.
     * @param hotelId the ID of the hotel to check for the user's reservations.
     * @return {@code true} if the user has one or more reservations at the specified hotel;
     *         {@code false} otherwise.
     */
    public boolean userHasReservation(Long userId, Long hotelId) {
        return reservationRepository.existsByUserIdAndRoomHotelId(userId, hotelId);
    }

    /**
     * Changes the reservation from an old room to a new room for a given user.
     *
     * @param userId the ID of the user whose reservation is being changed.
     * @param hotelId the ID of the hotelId.
     * @param newRoomId the ID of the new room to assign.
     * @return the updated {@link Reservation} object.
     * @throws IllegalArgumentException if no reservation is found or the new room is not found.
     * @throws IllegalStateException if the new room is not available.
     */
    public Reservation changeReservation(Long userId, Long hotelId, Long newRoomId) {
        Optional<Reservation> reservationOpt = reservationRepository.findByUserIdAndHotelId(userId, hotelId);
        Optional<Room> newRoomOpt = roomRepository.findById(newRoomId);

        if (reservationOpt.isEmpty()) {
            throw new IllegalArgumentException("No reservation found for the user in the specified hotel");
        }

        if (newRoomOpt.isEmpty()) {
            throw new IllegalArgumentException("New room not found");
        }

        Room newRoom = newRoomOpt.get();
        if (!newRoom.isAvailable()) {
            throw new IllegalStateException("New room is not available");
        }

        Reservation reservation = reservationOpt.get();
        Room oldRoom = reservation.getRoom();

        // Mark old room as available
        oldRoom.setAvailable(true);
        roomRepository.save(oldRoom);

        // Update reservation with new room
        reservation.setRoomId(newRoomId);
        reservation.setRoom(newRoom);

        // Mark new room as unavailable
        newRoom.setAvailable(false);
        roomRepository.save(newRoom);

        // Save and return the updated reservation
        return reservationRepository.save(reservation);
    }

    /**
     * Retrieves a list of reservations for a given user.
     *
     * @param userId the ID of the user.
     * @return a list of {@link Reservation} objects.
     */
    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationRepository.findByUserId(userId);
    }

    /**
     * Retrieves a reservation by its ID and user ID.
     *
     * @param reservationId the ID of the reservation.
     * @param userId the ID of the user.
     * @return the {@link Reservation} object.
     */
    public Reservation getReservationByIdAndUserId(Long reservationId, Long userId) {
        return reservationRepository.findByIdAndUserId(reservationId, userId);
    }

    /**
     * Cancels a reservation and marks the associated room as available.
     *
     * @param reservation the {@link Reservation} object to be canceled.
     */
    public void cancelReservation(Reservation reservation) {
        Room room = reservation.getRoom();
        if (room != null) {
            room.setAvailable(true);
            roomRepository.save(room);
        }
        reservationRepository.delete(reservation);
    }

    /**
     * Retrieves a list of available rooms in a specified hotel for a given date range.
     *
     * This method filters rooms to return only those that are available during the specified time period,
     * excluding rooms that are reserved within the same date range.
     *
     * @param hotelId the ID of the hotel to check for available rooms.
     * @param startDate the start date of the period for which room availability is being checked.
     * @param endDate the end date of the period for which room availability is being checked.
     * @return a {@link List} of {@link Room} objects that are available during the specified period.
     */
    public List<Room> getAvailableRooms(Long hotelId, LocalDateTime startDate, LocalDateTime endDate) {
        // Retrieve all rooms in the specified hotel
        List<Room> allRooms = roomRepository.findAvailableRoomsByHotelId(hotelId);

        // Fetch reservations that conflict with the specified date range
        List<Reservation> conflictingReservations = reservationRepository.findConflictingReservations(hotelId, startDate, endDate);

        // Extract the IDs of rooms that are reserved during the specified period
        Set<Long> reservedRoomIds = conflictingReservations.stream()
                .map(Reservation::getRoomId)
                .collect(Collectors.toSet());

        // Filter out rooms that are reserved during the specified period
        return allRooms.stream()
                .filter(room -> !reservedRoomIds.contains(room.getId()))
                .collect(Collectors.toList());
    }

    /**
     * Processes the check-out of a room by updating its availability and removing reservations.
     *
     * This method updates the availability status of the specified room to available and deletes
     * all active reservations associated with the room.
     *
     * @param roomId the ID of the room that is being checked out.
     * @throws RuntimeException if the room is not found in the database.
     */
    public void checkOut(Long roomId) {
        // Retrieve the room from the database. If not found, throw an exception.
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Find and delete all reservations associated with the specified room
        List<Reservation> reservations = reservationRepository.findByRoomId(roomId);
        reservations.forEach(reservationRepository::delete);

        // Update the room's availability status to true
        room.setAvailable(true);
        // Save the updated room information to the database
        roomRepository.save(room);
    }
}
