package user.store.management.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.store.management.dto.UserDTO;
import user.store.management.entity.ProdottoAcquistato;
import user.store.management.entity.User;
import user.store.management.exception.UserNotFoundException;
import user.store.management.service.ProdottoService;
import user.store.management.service.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@Api(value = "User Management System")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ProdottoService prodottoService;

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
        userService.aggiornaBudget(user, userDTO.getImportoTotaleSpeso()); // aggiunto il metodo aggiornaBudget
        return ResponseEntity.ok(user);
    }

    @ApiOperation(value = "Rimuovi cliente")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}/prodotti-acquistati")
    @ApiOperation(value = "Get the products purchased by the user")
    public Set<ProdottoAcquistato> getProdottiAcquistati(@PathVariable Long userId) {
        return userService.getProdottiAcquistati(userId);
    }

}
