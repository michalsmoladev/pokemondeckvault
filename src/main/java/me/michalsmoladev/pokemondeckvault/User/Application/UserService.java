package me.michalsmoladev.pokemondeckvault.User.Application;

import me.michalsmoladev.pokemondeckvault.Core.Application.JWT.JwtService;
import me.michalsmoladev.pokemondeckvault.User.Application.DTO.LoginUserDTO;
import me.michalsmoladev.pokemondeckvault.User.Application.DTO.RegisterUserDTO;
import me.michalsmoladev.pokemondeckvault.User.Application.DTO.UpdateUserDTO;
import me.michalsmoladev.pokemondeckvault.User.Domain.Entity.User;
import me.michalsmoladev.pokemondeckvault.User.Domain.Entity.UserRepositoryInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepositoryInterface userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public void createUser(RegisterUserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.username);
        user.setPassword(this.encodePassword(userDTO.password));
        user.setEmail(userDTO.email);

        this.userRepository.save(user);
    }

    public String loginUser(LoginUserDTO loginUserDTO) {
        User user = this.userRepository.findByEmail(loginUserDTO.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return this.jwtService.generateToken(user);
    }

    public void updateUser(UpdateUserDTO updateUserDTO) {
        Optional<User> user = this.userRepository.findById(updateUserDTO.id);

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }


        logger.debug(String.valueOf(!user.get().getEmail().equals(updateUserDTO.email) && this.userRepository.findByEmail(updateUserDTO.email).isEmpty()));
        if (!user.get().getEmail().equals(updateUserDTO.email) && this.userRepository.findByEmail(updateUserDTO.email).isEmpty()) {
            user.get().setEmail(updateUserDTO.email);
        }

        logger.debug(String.valueOf(!user.get().getUsername().equals(updateUserDTO.username) && this.userRepository.findByUsername(updateUserDTO.username).isEmpty()));
        if  (!user.get().getUsername().equals(updateUserDTO.username) && this.userRepository.findByUsername(updateUserDTO.username).isEmpty()) {
            user.get().setUsername(updateUserDTO.username);
        }

        user.get().setPassword(this.encodePassword(updateUserDTO.password));

        userRepository.save(user.get());
    }

    private String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }
}
