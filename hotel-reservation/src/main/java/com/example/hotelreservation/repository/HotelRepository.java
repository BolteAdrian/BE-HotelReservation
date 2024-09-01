package com.example.hotelreservation.repository;

import com.example.hotelreservation.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Hotel} entities.
 * This interface extends {@link JpaRepository} to provide standard CRUD operations for the {@link Hotel} entity.
 */
public interface HotelRepository extends JpaRepository<Hotel, Long> {
}
