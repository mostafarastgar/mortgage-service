package com.mostafa.ing.mortgage.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User API", description = "Operations related to users")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Returns a single user by their ID")
    public String getUserById(
            @Parameter(description = "ID of the user to be retrieved", example = "42")
            @PathVariable String id) {
        return "User with ID: " + id;
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Creates a user with the given information")
    public String createUser(
            @RequestBody
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User object to be created")
            String user) {
        return "Created user: " + user;
    }
}