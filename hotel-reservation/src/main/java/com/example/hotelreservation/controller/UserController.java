package com.example.hotelreservation.controller;

import com.example.hotelreservation.model.Reservation;
import com.example.hotelreservation.model.User;
import com.example.hotelreservation.service.UserService;
import com.example.hotelreservation.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling user-related operations, including retrieving user details,
 * getting user reservations, and canceling reservations.
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;
    private final ReservationService reservationService;

    // Constructor injection for UserService and ReservationService
    public UserController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    /**
     * Retrieves the details of the currently authenticated user.
     *
     * @param authentication the authentication object containing user details.
     * @return a {@link ResponseEntity} containing user details if the user is found,
     *         or a NOT_FOUND status if the user does not exist.
     */
    @GetMapping("/details")
    public ResponseEntity<?> getUserDetails(Authentication authentication) {
        // Fetch user details based on the authenticated user's username
        User user = userService.getUserByUsername(authentication.getName());

        // Return NOT_FOUND status if the user does not exist
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Return the user details if found
        return ResponseEntity.ok(user);
    }

    /**
     * Retrieves the reservations of the currently authenticated user.
     *
     * @param authentication the authentication object containing user details.
     * @return a {@link ResponseEntity} containing a list of reservations if the user is found,
     *         or a NOT_FOUND status if the user does not exist.
     */
    @GetMapping("/reservations")
    public ResponseEntity<?> getUserReservations(Authentication authentication) {
        // Fetch user details based on the authenticated user's username
        User user = userService.getUserByUsername(authentication.getName());

        // Return NOT_FOUND status if the user does not exist
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        // Retrieve the reservations associated with the user
        List<Reservation> reservations = reservationService.getReservationsByUserId(user.getId());

        // Return the list of reservations
        return ResponseEntity.ok(reservations);
    }

    /**
     * Cancels a specific reservation for the currently authenticated user.
     *
     * @param reservationId the ID of the reservation to be canceled.
     * @param authentication the authentication object containing user details.
     * @return a {@link ResponseEntity} indicating the result of the cancellation operation.
     *         Returns UNAUTHORIZED status if the user does not exist, or NOT_FOUND status if
     *         the reservation does not belong to the user.
     */
    @DeleteMapping("/reservations/{reservationId}")
    public ResponseEntity<?> cancelReservation(@PathVariable Long reservationId, Authentication authentication) {
        // Fetch user details based on the authenticated user's username
        User user = userService.getUserByUsername(authentication.getName());

        // Return UNAUTHORIZED status if the user does not exist
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }

        // Fetch the reservation based on reservation ID and user ID
        Reservation reservation = reservationService.getReservationByIdAndUserId(reservationId, user.getId());

        // Return NOT_FOUND status if the reservation does not exist
        if (reservation == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reservation not found");
        }

        // Cancel the reservation and mark the room as available
        reservationService.cancelReservation(reservation);

        // Return a success message
        return ResponseEntity.ok("Reservation canceled and room marked as available");
    }
}
