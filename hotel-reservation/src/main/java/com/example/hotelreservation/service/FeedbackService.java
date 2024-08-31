package com.example.hotelreservation.service;

import com.example.hotelreservation.model.Feedback;
import com.example.hotelreservation.repository.FeedbackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }
    public Feedback leaveFeedback(Long hotelId, Long userId, String comment, int rating) {
        Feedback feedback = new Feedback();
        feedback.setHotelId(hotelId);
        feedback.setUserId(userId);
        feedback.setComment(comment);
        feedback.setRating(rating);
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbacks(Long hotelId) {
        return feedbackRepository.findByHotelId(hotelId);
    }
}
