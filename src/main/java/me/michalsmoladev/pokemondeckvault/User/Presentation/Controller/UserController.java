package me.michalsmoladev.pokemondeckvault.User.Presentation.Controller;

import me.michalsmoladev.pokemondeckvault.User.Application.DTO.LoginUserDTO;
import me.michalsmoladev.pokemondeckvault.User.Application.DTO.RegisterUserDTO;
import me.michalsmoladev.pokemondeckvault.User.Application.DTO.TokenDTO;
import me.michalsmoladev.pokemondeckvault.User.Application.DTO.UpdateUserDTO;
import me.michalsmoladev.pokemondeckvault.User.Application.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/auth/register")
    public ResponseEntity register(@RequestBody RegisterUserDTO registerUserDTO) {
        this.userService.createUser(registerUserDTO);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginUserDTO loginUserDTO) {
        TokenDTO tokenDTO = new TokenDTO(this.userService.loginUser(loginUserDTO));
        return ResponseEntity.ok(tokenDTO);
    }

    @PatchMapping("/api/user/{id}")
    public ResponseEntity<String> updateUserAction(@PathVariable String id, @RequestBody UpdateUserDTO updateUserDTO) {
        updateUserDTO.id = UUID.fromString(id);

        this.userService.updateUser(updateUserDTO);

        return ResponseEntity.ok().build();
    }
}
