package com.user.usermanagement.controller;

import com.user.usermanagement.dto.UserDTO;
import com.user.usermanagement.entity.User;
import com.user.usermanagement.exception.UserNotFoundException;
import com.user.usermanagement.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(value = "User Management System")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "Aggiungi cliente")
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.created(URI.create("/users/" + createdUser.getId()))
                .body(createdUser);
    }

    @ApiOperation(value = "Vedi cliente tramite id")
    @GetMapping("/users/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @ApiOperation(value = "Vedi lista clienti")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @ApiOperation(value = "Aggiorna lo status del cliente in base all'importo totale speso")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateClientStatus(@PathVariable Long id, @RequestBody UserDTO userDTO) throws UserNotFoundException {
        User user = userService.getUser(id);
        userService.updateClientStatus(user, userDTO.getImportoTotaleSpeso());
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Rimuovi cliente")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
