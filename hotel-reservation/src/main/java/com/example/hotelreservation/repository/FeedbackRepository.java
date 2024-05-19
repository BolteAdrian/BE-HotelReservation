package com.example.hotelreservation.repository;

import com.example.hotelreservation.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}
