package com.example.hotelreservation.modelDto;

/**
 * Data Transfer Object (DTO) for submitting feedback.
 * This class encapsulates the details required to submit feedback for a hotel,
 * including the user's ID, the comment, and the rating.
 */
public class FeedbackDto {

    // Unique identifier of the user providing the feedback
    private Long userId;

    // The comment provided by the user regarding their experience
    private String comment;

    // The rating given by the user, typically on a scale (e.g., 1 to 5)
    private int rating;

    // Getters and Setters

    /**
     * Gets the unique identifier of the user providing the feedback.
     *
     * @return the user ID.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the unique identifier of the user providing the feedback.
     *
     * @param userId the user ID to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the comment provided by the user regarding their experience.
     *
     * @return the comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment provided by the user regarding their experience.
     *
     * @param comment the comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the rating given by the user, typically on a scale (e.g., 1 to 5).
     *
     * @return the rating.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating given by the user, typically on a scale (e.g., 1 to 5).
     *
     * @param rating the rating to set.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}
