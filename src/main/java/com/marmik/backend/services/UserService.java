package com.marmik.backend.services;

import com.marmik.backend.User;
import com.marmik.backend.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Create a new user
    public User createUser(User user) throws Exception {
        // Check if username already exists
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new Exception("Username already exists.");
        }

        // Save the new user
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Find a user by ID
    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    // Find a user by username
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Find a user by role
    public List<User> findByRole(String role) {
        return userRepository.findByRole(role);
    }

    // Update a user
    public User updateUser(ObjectId id, User updatedUser) throws Exception {
        // Check if the user with the given ID exists
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isEmpty()) {
            throw new Exception("User not found.");
        }

        // Ensure that the username is not already taken by another user
        Optional<User> userWithSameUsername = userRepository.findByUsername(updatedUser.getUsername());
        if (userWithSameUsername.isPresent() && !userWithSameUsername.get().getId().equals(id)) {
            throw new Exception("Username already exists.");
        }

        // Update the user and save
        updatedUser.setId(id);  // Ensure the ID remains the same
        return userRepository.save(updatedUser);
    }

    // Delete a user
    public void deleteUser(ObjectId id) throws Exception {
        // Check if the user exists
        if (!userRepository.existsById(id)) {
            throw new Exception("User not found.");
        }

        // Delete the user
        userRepository.deleteById(id);
    }
}
