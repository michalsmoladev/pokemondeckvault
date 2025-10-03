package me.michalsmoladev.pokemondeckvault.User.Application.DTO;

import me.michalsmoladev.pokemondeckvault.User.Domain.Entity.User;

import java.time.LocalDateTime;

public class UserDTO {
    public String username;
    public String email;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;

    public static UserDTO fromEntity(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.username = user.getUsername();
        userDTO.email = user.getEmail();
        userDTO.createdAt = user.getCreatedAt();
        userDTO.updatedAt = user.getUpdatedAt();

        return userDTO;
    }
}
