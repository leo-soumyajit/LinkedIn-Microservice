package com.soumyajit.LinkedInMicroservice.userService.Service;

import com.soumyajit.LinkedInMicroservice.userService.DTOS.LoginRequestDto;
import com.soumyajit.LinkedInMicroservice.userService.DTOS.SignupRequestDto;
import com.soumyajit.LinkedInMicroservice.userService.DTOS.UserDto;
import com.soumyajit.LinkedInMicroservice.userService.Entities.Enum.Roles;
import com.soumyajit.LinkedInMicroservice.userService.Entities.User;
import com.soumyajit.LinkedInMicroservice.userService.Exceptions.BadRequestException;
import com.soumyajit.LinkedInMicroservice.userService.Exceptions.ResourceNotFound;
import com.soumyajit.LinkedInMicroservice.userService.Repository.UserRepository;
import com.soumyajit.LinkedInMicroservice.userService.Security.JwtService;
import com.soumyajit.LinkedInMicroservice.userService.Utils.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.util.Set;

import static com.soumyajit.LinkedInMicroservice.userService.Utils.BCrypt.hashString;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserDto signup(SignupRequestDto signupRequestDto) {

        log.info("Signup a user with email: {}",signupRequestDto.getEmail());

        User user = modelMapper.map(signupRequestDto, User.class);

        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if (exists) throw new BadRequestException("User with this email already present");

        String hashedPassword = hashString(signupRequestDto.getPassword());
        user.setPassword(hashedPassword);
        user.setRoles(Set.of(Roles.USER));

        user = userRepository.save(user);
        log.info("saved the user with email: {}",signupRequestDto.getEmail());
        return modelMapper.map(user, UserDto.class);
    }

    public String login(LoginRequestDto loginRequestDto) {
        log.info("Login request for user with email {}",loginRequestDto.getEmail());

        User user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow(()->
                new BadRequestException("Incorrect email or password"));

        log.info("Checking user's password is valid or not");
        boolean isPasswordMatch = BCrypt.match(loginRequestDto.getPassword(), user.getPassword());

        if (!isPasswordMatch){
            throw new BadRequestException("Incorrect email or password");
        }

        log.info("User's password is valid now return Access Token");

        return jwtService.generateAccessToken(user);

    }
}
