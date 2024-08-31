package com.example.hotelreservation.repository;

import com.example.hotelreservation.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repository interface for accessing {@link Feedback} entities.
 */
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    /**
     * Finds feedbacks by hotel ID.
     *
     * @param hotelId the ID of the hotel.
     * @return a list of {@link Feedback} entities associated with the given hotel ID.
     */
    List<Feedback> findByHotelId(Long hotelId);
}
