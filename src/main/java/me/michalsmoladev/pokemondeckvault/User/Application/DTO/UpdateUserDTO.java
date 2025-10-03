package me.michalsmoladev.pokemondeckvault.User.Application.DTO;

import java.util.UUID;

public class UpdateUserDTO {
    public UUID id;
    public String username;
    public String password;
    public String email;

    public UpdateUserDTO(UUID id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
