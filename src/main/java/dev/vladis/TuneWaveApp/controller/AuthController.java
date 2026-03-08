package dev.vladis.TuneWaveApp.controller;


import dev.vladis.TuneWaveApp.document.User;
import dev.vladis.TuneWaveApp.dto.AuthRequest;
import dev.vladis.TuneWaveApp.dto.AuthResponse;
import dev.vladis.TuneWaveApp.dto.RegisterRequest;
import dev.vladis.TuneWaveApp.dto.UserResponse;
import dev.vladis.TuneWaveApp.service.AppUserDetailsService;
import dev.vladis.TuneWaveApp.service.UserService;
import dev.vladis.TuneWaveApp.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            User existingUser = userService.findByEmail(authRequest.getEmail());


            String token = jwtUtil.generateToken(userDetails, existingUser.getRole().name());

            return ResponseEntity.ok(new AuthResponse(token, authRequest.getEmail(), existingUser.getRole().name()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Incorrect email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest) {
        try {
            UserResponse response = userService.registerUser(registerRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
}
