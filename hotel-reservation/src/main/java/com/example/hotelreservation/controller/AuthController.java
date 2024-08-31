package com.example.hotelreservation.controller;

import com.example.hotelreservation.modelDto.AuthenticationRequest;
import com.example.hotelreservation.modelDto.AuthenticationResponse;
import com.example.hotelreservation.model.User;
import com.example.hotelreservation.repository.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticates the user and returns a JWT token if successful.
     *
     * @param authenticationRequest the authentication request containing the username and password.
     * @return a {@link ResponseEntity} containing the JWT token if authentication is successful.
     * @throws Exception if the authentication fails due to incorrect credentials.
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            // Authenticate the user using the provided username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            // Throw an exception if the credentials are incorrect
            throw new Exception("Incorrect username or password", e);
        }

        // Load the user details from the database
        final UserDetails userDetails = userService
                .loadUserByUsername(authenticationRequest.getUsername());

        // Generate a JWT token based on the user details
        final String jwt = jwtUtil.generateToken(userDetails);

        // Return the generated JWT token in the response
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    /**
     * Registers a new user by saving their details in the database.
     * The user's password is encoded before being stored.
     *
     * @param user the user details to be registered.
     * @return a {@link ResponseEntity} with a success message upon successful registration.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        // Encode the user's password before saving it to the database
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save the user details in the database
        userRepository.save(user);

        // Return a success message
        return ResponseEntity.ok("User registered successfully");
    }
}
