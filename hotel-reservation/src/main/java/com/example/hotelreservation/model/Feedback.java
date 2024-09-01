package com.example.hotelreservation.model;

import jakarta.persistence.*;

/**
 * Entity class representing feedback submitted by users for hotels.
 */
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(length = 1000) // Assuming comments could be lengthy
    private String comment;

    @Column(nullable = false)
    private int rating; // Rating value, typically from 1 to 5

    // Getters and setters

    /**
     * Gets the unique identifier of the feedback.
     *
     * @return the ID of the feedback.
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the feedback.
     *
     * @param id the ID to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the hotel associated with this feedback.
     *
     * @return the {@link Hotel} associated with this feedback.
     */
    public Hotel getHotel() {
        return hotel;
    }

    /**
     * Sets the hotel associated with this feedback.
     *
     * @param hotel the {@link Hotel} to set.
     */
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    /**
     * Gets the ID of the user who submitted the feedback.
     *
     * @return the user ID.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Sets the ID of the user who submitted the feedback.
     *
     * @param userId the user ID to set.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * Gets the comment provided in the feedback.
     *
     * @return the feedback comment.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment provided in the feedback.
     *
     * @param comment the feedback comment to set.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Gets the rating provided in the feedback.
     *
     * @return the rating value.
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating provided in the feedback.
     *
     * @param rating the rating value to set.
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}
