package com.soumyajit.LinkedInMicroservice.userService.Service;

import com.soumyajit.LinkedInMicroservice.userService.DTOS.SignupRequestDto;
import com.soumyajit.LinkedInMicroservice.userService.DTOS.UserDto;
import com.soumyajit.LinkedInMicroservice.userService.Entities.User;
import com.soumyajit.LinkedInMicroservice.userService.Exceptions.BadRequestException;
import com.soumyajit.LinkedInMicroservice.userService.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import static com.soumyajit.LinkedInMicroservice.userService.Utils.BCrypt.hashString;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserDto signup(SignupRequestDto signupRequestDto) {

        log.info("Signup a user with email: {}",signupRequestDto.getEmail());

        User user = modelMapper.map(signupRequestDto, User.class);

        boolean exists = userRepository.existsByEmail(signupRequestDto.getEmail());
        if (exists) throw new BadRequestException("User with this email already present");

        String hashedPassword = hashString(signupRequestDto.getPassword());
        user.setPassword(hashedPassword);

        user = userRepository.save(user);
        log.info("saved the user with email: {}",signupRequestDto.getEmail());
        return modelMapper.map(user, UserDto.class);
    }
}
