package com.example.hotelreservation.repository;

import com.example.hotelreservation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link User} entities.
 * This interface extends {@link JpaRepository} to provide standard CRUD operations and custom queries for {@link User} entities.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a {@link User} by their username.
     *
     * This method retrieves a user entity based on the provided username.
     *
     * @param username the username of the user to find.
     * @return the {@link User} entity associated with the given username, or {@code null} if no such user exists.
     */
    User findByUsername(String username);
}
