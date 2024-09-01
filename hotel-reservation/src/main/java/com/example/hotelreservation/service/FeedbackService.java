package com.example.hotelreservation.service;

import com.example.hotelreservation.model.Feedback;
import com.example.hotelreservation.model.Hotel;
import com.example.hotelreservation.modelDto.FeedbackDto;
import com.example.hotelreservation.repository.FeedbackRepository;
import com.example.hotelreservation.repository.HotelRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing feedback operations related to hotels.
 * Provides methods for leaving feedback and retrieving feedback for a specific hotel.
 */
@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final HotelRepository hotelRepository;

    /**
     * Constructor for FeedbackService.
     *
     * @param feedbackRepository the repository used to manage feedback data.
     * @param hotelRepository the repository used to manage hotel data.
     */
    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, HotelRepository hotelRepository) {
        this.feedbackRepository = feedbackRepository;
        this.hotelRepository = hotelRepository;
    }

    /**
     * Allows a user to leave feedback for a specific hotel.
     *
     * @param hotelId the ID of the hotel for which feedback is being left.
     * @param userId the ID of the user leaving the feedback.
     * @param comment the comment text of the feedback.
     * @param rating the rating given in the feedback.
     * @return the saved Feedback object.
     * @throws IllegalArgumentException if the hotel ID is invalid (i.e., the hotel does not exist).
     */
    @Transactional
    public Feedback leaveFeedback(Long hotelId, Long userId, String comment, int rating) {
        // Fetch the hotel entity using the hotelId
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hotel ID: " + hotelId));

        // Create a new feedback object
        Feedback feedback = new Feedback();
        feedback.setHotel(hotel);  // Set the hotel entity
        feedback.setUserId(userId);
        feedback.setComment(comment);
        feedback.setRating(rating);

        // Save and return the feedback
        return feedbackRepository.save(feedback);
    }

    /**
     * Retrieves all feedback for a specific hotel.
     *
     * This method queries the repository to find all feedback entries associated with the given hotel ID.
     * It then converts each Feedback entity into a FeedbackDto for a simplified representation
     * of the feedback data, which is then returned as a list.
     *
     * @param hotelId the ID of the hotel for which feedback is being retrieved.
     *               This ID is used to query the database for associated feedback entries.
     * @return a list of FeedbackDto objects representing feedback for the specified hotel.
     *         Each FeedbackDto contains the user ID, comment, and rating for the feedback.
     */
    public List<FeedbackDto> getFeedbacks(Long hotelId) {
        // Retrieve all feedback entities associated with the given hotel ID from the repository
        List<Feedback> feedbacks = feedbackRepository.findByHotelId(hotelId);

        // Convert each Feedback entity to a FeedbackDto and collect them into a list
        return feedbacks.stream()
                .map(this::convertToDto) // Convert each Feedback to FeedbackDto
                .collect(Collectors.toList()); // Collect all FeedbackDto objects into a list
    }

    /**
     * Converts a Feedback entity into a FeedbackDto.
     *
     * This method extracts the relevant fields from a Feedback entity and maps them to a FeedbackDto.
     * The conversion is necessary to avoid exposing internal entity details and to provide a simpler
     * data structure for clients consuming the feedback data.
     *
     * @param feedback the Feedback entity to be converted into a DTO.
     *                 This entity contains detailed feedback information such as user ID, comment, and rating.
     * @return a FeedbackDto containing the user ID, comment, and rating from the provided Feedback entity.
     */
    private FeedbackDto convertToDto(Feedback feedback) {
        // Create a new instance of FeedbackDto
        FeedbackDto dto = new FeedbackDto();

        // Set the user ID from the Feedback entity to the DTO
        dto.setUserId(feedback.getUserId());

        // Set the comment from the Feedback entity to the DTO
        dto.setComment(feedback.getComment());

        // Set the rating from the Feedback entity to the DTO
        dto.setRating(feedback.getRating());

        // Return the populated FeedbackDto
        return dto;
    }
}
