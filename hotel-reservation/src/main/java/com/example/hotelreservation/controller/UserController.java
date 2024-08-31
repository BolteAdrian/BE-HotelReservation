package com.example.hotelreservation.controller;

import com.example.hotelreservation.model.Reservation;
import com.example.hotelreservation.model.User;
import com.example.hotelreservation.repository.ReservationRepository;
import com.example.hotelreservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling user-related operations.
 * This includes fetching user details, retrieving reservations, and canceling reservations.
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200") // CORS annotation to allow requests from the Angular frontend.
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Retrieves the details of the authenticated user.
     *
     * @param authentication the authentication token containing the user's credentials.
     * @return a {@link ResponseEntity} containing the user details if found, or a NOT_FOUND status if the user is not found.
     */
    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        // Find the user by their username
        User user = userRepository.findByUsername(authentication.getName());
        if (user == null) {
            // Return a 404 status if the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Return the user details in the response
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves the list of reservations for the authenticated user.
     *
     * @param authentication the authentication token containing the user's credentials.
     * @return a {@link ResponseEntity} containing the list of reservations, or a NOT_FOUND status if the user is not found.
     */
    @GetMapping("/reservations")
    public ResponseEntity<?> getUserReservations(Authentication authentication) {
        // Find the user by their username
        User user = userRepository.findByUsername(authentication.getName());
        if (user == null) {
            // Return a 404 status if the user is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Retrieve the reservations associated with the user's ID
        List<Reservation> reservations = reservationRepository.findByUserId(user.getId());

        // Return the list of reservations in the response
        return ResponseEntity.ok(reservations);
    }

    /**
     * Cancels a reservation for the authenticated user.
     *
     * @param reservationId  the ID of the reservation to be canceled.
     * @param authentication the authentication token containing the user's credentials.
     * @return a {@link ResponseEntity} indicating the result of the cancellation, or a NOT_FOUND status if the reservation is not found.
     */
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId, Authentication authentication) {
        // Find the user by their username
        User user = userRepository.findByUsername(authentication.getName());
        // Find the reservation by its ID and the user's ID
        Reservation reservation = reservationRepository.findByIdAndUserId(reservationId, user.getId());

        if (reservation == null) {
            // Return a 404 status if the reservation is not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }

        // Delete the reservation
        reservationRepository.delete(reservation);
        // Return a success message
        return ResponseEntity.ok("Reservation canceled");
    }
}
