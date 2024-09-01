package com.example.hotelreservation.controller;

import com.example.hotelreservation.modelDto.AuthenticationRequest;
import com.example.hotelreservation.modelDto.AuthenticationResponse;
import com.example.hotelreservation.model.User;
import com.example.hotelreservation.security.JwtUtil;
import com.example.hotelreservation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling authentication-related operations.
 * This includes login and registration functionality.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticates the user and returns a JWT token if successful.
     *
     * @param authenticationRequest the authentication request containing the username and password.
     * @return a {@link ResponseEntity} containing the JWT token and user ID if authentication is successful.
     * @throws Exception if the authentication fails due to incorrect credentials.
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            // Attempt to authenticate the user with the provided username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // Throw an exception if the credentials are incorrect
            throw new Exception("Incorrect username or password", e);
        }

        // Load the user details from the database using the username
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        // Generate a JWT token for the authenticated user
        final String jwtToken = jwtUtil.generateToken(userDetails);

        // Retrieve the user from the UserDetails object
        // Assuming your userDetails have the user as part of their username or as a custom field
        // Otherwise, query the User object from the database
        User user = userService.getUserByUsername(authenticationRequest.getUsername());

        // Return the JWT token and user ID in the response
        return ResponseEntity.ok(new AuthenticationResponse(jwtToken, user.getId()));
    }

    /**
     * Registers a new user by saving their details in the database.
     * The user's password is encoded before being stored.
     *
     * @param user the user details to be registered.
     * @return a {@link ResponseEntity} with a success message upon successful registration.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerNewUser(@RequestBody User user) {
        // Encode the user's password before saving it to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the new user details in the database
        userService.registerUser(user);

        // Return a success message
        return ResponseEntity.ok("User registered successfully");
    }
}
