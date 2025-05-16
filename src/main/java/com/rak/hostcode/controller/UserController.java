package com.rak.hostcode.controller;

import com.rak.hostcode.dto.UserDTO;
import com.rak.hostcode.model.User;
import com.rak.hostcode.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<?>> getAllUsers() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/{uid}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable int uid) {
        UserDTO user = userService.getUserById(uid);
        return ResponseEntity.ok().body(user);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        UserDTO savedUser = userService.registerUser(user);
        if (savedUser == null) {
            HashMap<String, String> errorMessage = new HashMap<>();
            errorMessage.put("message", "Error occurred while registering user");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }
}
