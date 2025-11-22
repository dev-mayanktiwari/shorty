package tech.mayanktiwari.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.experimental.FieldDefaults;
import tech.mayanktiwari.auth.dto.response.RegisterRequest;
import tech.mayanktiwari.auth.dto.response.UserResponseDto;
import tech.mayanktiwari.auth.mapper.UserMapper;
import tech.mayanktiwari.auth.models.Users;
import tech.mayanktiwari.auth.repository.UserRepository;
import tech.mayanktiwari.auth.security.JwtUtil;
import tech.mayanktiwari.common.exception.BusinessException;
import tech.mayanktiwari.common.exception.ErrorCodes;

@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AuthService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    public UserResponseDto registerUser(RegisterRequest registerRequest) {

        userRepository.findByEmailOrUsername(registerRequest.getEmail(), registerRequest.getUsername())
                      .ifPresent(u -> {
                          throw new BusinessException(ErrorCodes.USER_ALREADY_EXISTS);
                      });

        return UserMapper.toResponseDto(userRepository.save(Users.builder()
                                                                 .username(registerRequest.getUsername())
                                                                 .email(registerRequest.getEmail())
                                                                 .password(passwordEncoder.encode(registerRequest.getPassword()))
                                                                 .name(registerRequest.getName())
                                                                 .build()));
    }

}
