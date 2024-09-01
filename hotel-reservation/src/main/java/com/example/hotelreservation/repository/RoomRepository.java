package com.example.hotelreservation.repository;

import com.example.hotelreservation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing {@link Room} entities.
 * This interface extends {@link JpaRepository} to provide standard CRUD operations and custom queries for {@link Room} entities.
 */
public interface RoomRepository extends JpaRepository<Room, Long> {

    /**
     * Finds all available rooms in a specified hotel.
     *
     * This custom query retrieves rooms that belong to the specified hotel and are marked as available.
     *
     * @param hotelId the ID of the hotel for which to find rooms.
     * @return a list of {@link Room} objects that are available in the specified hotel.
     */
    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId")
    List<Room> findAvailableRoomsByHotelId(Long hotelId);
}
