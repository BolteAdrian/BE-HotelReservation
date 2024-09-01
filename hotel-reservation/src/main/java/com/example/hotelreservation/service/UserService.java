package com.example.hotelreservation.service;

import com.example.hotelreservation.model.User;
import com.example.hotelreservation.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * Service class for managing user-related operations, including loading user details
 * and user registration.
 */
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Constructor for UserService.
     *
     * @param userRepository the repository used to manage user data.
     */
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves a user by their username.
     *
     * @param username the username of the user.
     * @return the {@link User} object.
     */
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Loads user-specific data from the database for authentication purposes.
     *
     * @param username the username of the user.
     * @return a {@link UserDetails} object containing user information.
     * @throws UsernameNotFoundException if no user is found with the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Convert User object to Spring Security's UserDetails object
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(SimpleGrantedAuthority::new) // Convert roles to authorities
                        .collect(Collectors.toList())
        );
    }

    /**
     * Registers a new user by saving the user details to the repository.
     *
     * @param user the {@link User} object to be registered.
     */
    public void registerUser(User user) {
        userRepository.save(user);
    }
}
