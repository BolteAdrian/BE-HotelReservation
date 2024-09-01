package com.example.hotelreservation.repository;

import com.example.hotelreservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Reservation} entities.
 * This interface extends {@link JpaRepository} to provide CRUD operations and custom query methods.
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    /**
     * Finds all reservations associated with a specific user ID.
     *
     * @param userId the ID of the user whose reservations are to be retrieved.
     * @return a {@link List} of {@link Reservation} objects associated with the given user ID.
     */
    List<Reservation> findByUserId(Long userId);

    /**
     * Finds a reservation by its ID and the associated user ID.
     *
     * @param id the ID of the reservation to be retrieved.
     * @param userId the ID of the user who made the reservation.
     * @return a {@link Reservation} object matching the given ID and user ID, or {@code null} if not found.
     */
    Reservation findByIdAndUserId(Long id, Long userId);

    /**
     * Finds a reservation made by a specific user in a given hotel.
     *
     * This method performs a query to find a reservation that matches the specified user ID and hotel ID.
     * It joins the reservation with the room to access the hotel's information. If a reservation exists
     * for the user in the specified hotel, it returns an {@link Optional} containing the reservation.
     * If no such reservation is found, it returns {@code Optional.empty()}.
     *
     * @param userId the ID of the user who made the reservation. Must not be {@code null}.
     * @param hotelId the ID of the hotel where the reservation is checked. Must not be {@code null}.
     * @return an {@link Optional} containing the {@link Reservation} if found, or {@code Optional.empty()} if not found.
     */
    @Query("SELECT r FROM Reservation r JOIN r.room ro WHERE r.userId = :userId AND ro.hotel.id = :hotelId")
    Optional<Reservation> findByUserIdAndHotelId(Long userId, Long hotelId);

    /**
     * Checks if a reservation exists for a specific user at a given hotel.
     *
     * This method uses a custom JPQL (Java Persistence Query Language) query to determine if
     * there is at least one reservation for the provided user ID at the specified hotel ID.
     * It returns {@code true} if one or more reservations are found; otherwise, it returns {@code false}.
     *
     * The query performs the following:
     * - Counts the number of reservations matching the given user ID and hotel ID.
     * - Returns {@code true} if the count is greater than 0, indicating that a reservation exists.
     *
     * @param userId the ID of the user for whom the reservation status is being checked.
     * @param hotelId the ID of the hotel where the reservation status is being checked.
     * @return {@code true} if there is at least one reservation for the specified user at the hotel;
     *         {@code false} otherwise.
     */
    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.userId = :userId AND r.room.hotel.id = :hotelId")
    boolean existsByUserIdAndRoomHotelId(@Param("userId") Long userId, @Param("hotelId") Long hotelId);

    /**
     * Finds reservations that conflict with a specified date range in a given hotel.
     *
     * This query retrieves reservations where the room is occupied at any time during the
     * specified start and end dates. It helps to determine if a room is reserved within a given
     * date range.
     *
     * @param hotelId the ID of the hotel where the reservations are checked.
     * @param startDate the start date of the period to check for conflicting reservations.
     * @param endDate the end date of the period to check for conflicting reservations.
     * @return a {@link List} of {@link Reservation} objects that overlap with the specified date range.
     */
    @Query("SELECT r FROM Reservation r WHERE r.room.hotel.id = :hotelId " +
            "AND (r.checkInTime < :endDate AND r.checkOutTime > :startDate)")
    List<Reservation> findConflictingReservations(
            @Param("hotelId") Long hotelId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    /**
     * Retrieves all reservations associated with a specific room.
     *
     * This query finds all reservations for the given room ID, regardless of the reservation
     * dates. It is used to check if there are any existing reservations for a room.
     *
     * @param roomId the ID of the room for which reservations are being retrieved.
     * @return a {@link List} of {@link Reservation} objects associated with the specified room.
     */
    @Query("SELECT r FROM Reservation r WHERE r.roomId = :roomId")
    List<Reservation> findByRoomId(@Param("roomId") Long roomId);

}
