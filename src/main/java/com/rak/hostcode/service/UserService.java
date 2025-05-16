package com.rak.hostcode.service;

import com.rak.hostcode.dto.UserDTO;
import com.rak.hostcode.exception.DuplicateFieldException;
import com.rak.hostcode.exception.UserNotFoundException;
import com.rak.hostcode.model.User;
import com.rak.hostcode.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO registerUser(User user) {
        if(userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateFieldException("A user with email as " + user.getEmail() + " already exists");
        }
        if(userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateFieldException("A user with username as " + user.getUsername() + " already exists");
        }
        if(userRepository.existsByMobile(user.getMobile())) {
            throw new DuplicateFieldException("A user with mobile as " + user.getMobile() + " already exists");
        }
        return convertUserToUserDTO(userRepository.save(user));
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(this::convertUserToUserDTO).collect(Collectors.toList());
    }

    public UserDTO getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with user id: " + id));
        return convertUserToUserDTO(user);

    }

    private UserDTO convertUserToUserDTO(User user) {
        return new UserDTO(user.getUid(), user.getUsername(), user.getEmail(), user.getMobile(), user.getFirstName(), user.getLastName(), user.getDateOfBirth(), user.getOrganization());
    }
}
