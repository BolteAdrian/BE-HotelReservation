package com.example.hotelreservation.modelDto;

/**
 * Data Transfer Object (DTO) for authentication responses.
 * This class encapsulates the information returned after a successful authentication,
 * including the JSON Web Token (JWT) and the user's ID.
 */
public class AuthenticationResponse {

    // JSON Web Token (JWT) used for authentication
    private final String jwt;

    // Unique identifier of the authenticated user
    private final Long userId;

    /**
     * Parameterized constructor to initialize AuthenticationResponse with
     * the provided JWT and user ID.
     *
     * @param jwt the JSON Web Token (JWT) for the authenticated session.
     * @param userId the unique identifier of the authenticated user.
     */
    public AuthenticationResponse(String jwt, Long userId) {
        this.jwt = jwt;
        this.userId = userId;
    }

    // Getters

    /**
     * Gets the JSON Web Token (JWT) for the authenticated session.
     *
     * @return the JWT.
     */
    public String getJwt() {
        return jwt;
    }

    /**
     * Gets the unique identifier of the authenticated user.
     *
     * @return the user ID.
     */
    public Long getUserId() {
        return userId;
    }
}
