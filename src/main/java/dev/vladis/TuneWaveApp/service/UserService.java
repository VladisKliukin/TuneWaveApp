package dev.vladis.TuneWaveApp.service;

import dev.vladis.TuneWaveApp.document.User;
import dev.vladis.TuneWaveApp.dto.RegisterRequest;
import dev.vladis.TuneWaveApp.dto.UserResponse;
import dev.vladis.TuneWaveApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse registerUser(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User newUser = User.builder()
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(User.Role.USER)
                .build();

        userRepository.save(newUser);
        return UserResponse.builder()
                .id(newUser.getId())
                .email(newUser.getEmail())
                .role(UserResponse.Role.USER)
                .build();
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
