package com.example.hotelreservation.modelDto;

/**
 * Data Transfer Object (DTO) for authentication requests.
 * This class is used to encapsulate the data required for user authentication
 * such as username and password.
 */
public class AuthenticationRequest {

    private String username;
    private String password;

    // Default constructor
    public AuthenticationRequest() {
    }

    /**
     * Parameterized constructor to initialize AuthenticationRequest with
     * provided username and password.
     *
     * @param username the username for authentication.
     * @param password the password for authentication.
     */
    public AuthenticationRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters and setters

    /**
     * Gets the username used for authentication.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username used for authentication.
     *
     * @param username the username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password used for authentication.
     *
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password used for authentication.
     *
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
