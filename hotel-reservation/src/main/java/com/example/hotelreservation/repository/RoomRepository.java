package com.example.hotelreservation.repository;

import com.example.hotelreservation.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT r FROM Room r WHERE r.hotel.id = :hotelId AND r.available = true")
    List<Room> findAvailableRoomsByHotelId(Long hotelId);
}
