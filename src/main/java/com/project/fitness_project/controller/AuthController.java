package com.project.fitness_project.controller;

import com.project.fitness_project.dto.LoginRequest;
import com.project.fitness_project.dto.LoginResponse;
import com.project.fitness_project.dto.RegisterRequestDTO;
import com.project.fitness_project.dto.RegisterResponse;
import com.project.fitness_project.model.User;
import com.project.fitness_project.security.CustomUserDetails;
import com.project.fitness_project.security.JwtUtils;
import com.project.fitness_project.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequestDTO registerRequest){
        RegisterResponse reisteredUser = userService.register(registerRequest);
        return ResponseEntity.ok(reisteredUser);
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication;

        try{

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                    )
            );

            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(401).build();
            }

//            User user = userService.authenticate(loginRequest);
//            String token = jwtUtils.generateToken(user.getId() , user.getRole().name());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        User user = customUserDetails.getUser();
        String token = jwtUtils.generateToken(user.getId() , user.getRole().name());

        return ResponseEntity.ok(new LoginResponse(token , userService.mapToResponse(user)));


    }
}
