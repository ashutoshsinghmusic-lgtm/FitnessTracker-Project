package com.project.fitness_project.service;

import com.project.fitness_project.dto.LoginRequest;
import com.project.fitness_project.dto.RegisterRequestDTO;
import com.project.fitness_project.dto.RegisterResponse;
import com.project.fitness_project.model.User;
import com.project.fitness_project.model.UserRole;
import com.project.fitness_project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public RegisterResponse register(RegisterRequestDTO request) {

          UserRole role = UserRole.USER ;
          if(request.getRole() != null)
              role = request.getRole();

          User user = User.builder()
                          .firstName(request.getFirstName())
                          .lastName(request.getLastName())
                          .email(request.getEmail())
                          .password(passwordEncoder.encode(request.getPassword()))
                          .role(role)
                  .build();

//        User user = new User(
//                null,
//                request.getEmail(),
//                request.getPassword(),
//                request.getFirstName(),
//                request.getLastName (),
//                Instant.parse( "2025-12-08T14:49:41.208Z")
//                        .atZone(ZoneOffset. UTC)
//                        . toLocalDateTime(),
//                Instant.parse("2025-12-08T14:49:41.208Z")
//                        .atZone (ZoneOffset. UTC)
//                        .toLocalDateTime (),
//                List.of(),
//                List.of()
//        );
        User savedUser =  userRepository.save(user);

        return mapToResponse(savedUser);
    }

    public RegisterResponse mapToResponse(User savedUser) {

        RegisterResponse response =
                RegisterResponse.builder()
                                .email(savedUser.getEmail())
                                .id(savedUser.getId())
                                .firstName(savedUser.getFirstName())
                                .lastName(savedUser.getLastName())
                                .createdDate(savedUser.getCreatedDate())
                                .updatedDate(savedUser.getUpdatedDate())
                        .build();



//        response.setId(savedUser.getId());
//        response.setEmail(savedUser.getEmail());
//        response.setFirstName(savedUser.getFirstName());
//        response.setLastName(savedUser.getLastName());
//        response.setStatus("Successfully Registered");

        return response;
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User authenticate(LoginRequest loginRequest) {
        User user = findByEmail(loginRequest.getEmail());

        if(user == null){
            throw new RuntimeException("User Can not be Empty");
        }

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new RuntimeException("Incorrect Password");
        }

        return user;

    }
}
