package tech.mayanktiwari.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.experimental.FieldDefaults;
import tech.mayanktiwari.auth.dto.request.LoginRequest;
import tech.mayanktiwari.auth.dto.request.RegisterRequest;
import tech.mayanktiwari.auth.dto.response.JwtResponse;
import tech.mayanktiwari.auth.dto.response.UserResponseDto;
import tech.mayanktiwari.auth.mapper.UserDtoMapper;
import tech.mayanktiwari.auth.security.JwtUtil;
import tech.mayanktiwari.common.exception.BusinessException;
import tech.mayanktiwari.common.exception.ErrorCodes;
import tech.mayanktiwari.database.entities.User;
import tech.mayanktiwari.database.repositories.UserRepository;

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

    @Autowired
    UserDtoMapper userDtoMapper;

    public UserResponseDto registerUser(RegisterRequest registerRequest) {

        userRepository.findByEmailOrUsername(registerRequest.getEmail(), registerRequest.getUsername())
                      .ifPresent(u -> {
                          throw new BusinessException(ErrorCodes.USER_ALREADY_EXISTS);
                      });

        return userDtoMapper.toDto(userRepository.save(User.builder()
                                                           .username(registerRequest.getUsername())
                                                           .email(registerRequest.getEmail())
                                                           .password(passwordEncoder.encode(registerRequest.getPassword()))
                                                           .name(registerRequest.getName())
                                                           .build()));
    }

    public JwtResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailOrUsername(loginRequest.getIdentifier(), loginRequest.getIdentifier())
                                  .orElseThrow(() -> new BusinessException(ErrorCodes.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCodes.INVALID_CREDENTIALS);
        }

        return new JwtResponse(jwtUtil.generateToken(user.getUsername()), user.getUsername(), user.getEmail());
    }

}
