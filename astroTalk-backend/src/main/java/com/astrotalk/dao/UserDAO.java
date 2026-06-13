package com.astrotalk.dao;

import com.astrotalk.entity.Role;
import com.astrotalk.entity.User;
import com.astrotalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
/**
 * Data access layer wrapping {@link UserRepository} for {@link User} entity operations.
 * Provides CRUD methods and specialized queries with logging and error handling.
 */
@Component
@RequiredArgsConstructor
public class UserDAO {

    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);
    private final UserRepository userRepository;

    /**
     * Persists a new or existing user.
     *
     * @param user the user entity to save
     * @return the saved user entity
     */
    public User save(User user) {
        log.debug("Saving user: {}", user.getEmail());
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            log.error("Error saving user: {}", user.getEmail(), e);
            throw e;
        }
    }

    public Optional<User> findById(Long id) {
        log.debug("Finding user by id: {}", id);
        try {
            return userRepository.findById(id);
        } catch (Exception e) {
            log.error("Error finding user by id: {}", id, e);
            throw e;
        }
    }

    /**
     * Finds a user by their email address.
     *
     * @param email the user's email
     * @return an Optional containing the user, or empty if not found
     */
    public Optional<User> findByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        try {
            return userRepository.findByEmail(email);
        } catch (Exception e) {
            log.error("Error finding user by email: {}", email, e);
            throw e;
        }
    }

    /**
     * Checks whether a user exists with the given email.
     *
     * @param email the email to check
     * @return true if a user with the email exists
     */
    public boolean existsByEmail(String email) {
        log.debug("Checking if user exists by email: {}", email);
        try {
            return userRepository.existsByEmail(email);
        } catch (Exception e) {
            log.error("Error checking user existence by email: {}", email, e);
            throw e;
        }
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    public List<User> findAll() {
        log.debug("Finding all users");
        try {
            return userRepository.findAll();
        } catch (Exception e) {
            log.error("Error finding all users", e);
            throw e;
        }
    }

    /**
     * Deletes the given user entity.
     *
     * @param user the user entity to delete
     */
    public void delete(User user) {
        log.debug("Deleting user: {}", user.getId());
        try {
            userRepository.delete(user);
        } catch (Exception e) {
            log.error("Error deleting user: {}", user.getId(), e);
            throw e;
        }
    }

    /**
     * Finds users by their role.
     *
     * @param role the role filter
     * @return a list of users with the given role
     */
    public List<User> findByRole(Role role) {
        log.debug("Finding users by role: {}", role);
        try {
            return userRepository.findByRole(role);
        } catch (Exception e) {
            log.error("Error finding users by role: {}", role, e);
            throw e;
        }
    }

    /**
     * Searches users by name or email with case-insensitive matching.
     *
     * @param name     the name search term
     * @param email    the email search term
     * @param pageable the pagination information
     * @return a page of matching users
     */
    public Page<User> searchByNameOrEmail(String name, String email, Pageable pageable) {
        log.debug("Searching users by name: {} or email: {}", name, email);
        try {
            return userRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(name, email, pageable);
        } catch (Exception e) {
            log.error("Error searching users by name: {} or email: {}", name, email, e);
            throw e;
        }
    }

    /**
     * Counts users created after the given date-time.
     *
     * @param dateTime the threshold date-time
     * @return the count of users created after
     */
    public long countByCreatedAtAfter(LocalDateTime dateTime) {
        log.debug("Counting users created after: {}", dateTime);
        try {
            return userRepository.countByCreatedAtAfter(dateTime);
        } catch (Exception e) {
            log.error("Error counting users created after: {}", dateTime, e);
            throw e;
        }
    }
}
