package com.ecommerce.user_service.Impl;

import com.ecommerce.user_service.Exception.ResourceAlreadyExistsException;
import com.ecommerce.user_service.Exception.ResourceNotFoundException;
import com.ecommerce.user_service.Repo.UserRepository;
import com.ecommerce.user_service.Service.UserService;
import com.ecommerce.user_service.Util.JwtUtil;
import com.ecommerce.user_service.dto.LoginRequestDto;
import com.ecommerce.user_service.dto.LoginResponseDto;
import com.ecommerce.user_service.dto.UserCreateRequestDto;
import com.ecommerce.user_service.dto.UserResponseDto;
import com.ecommerce.user_service.entity.User;
import com.ecommerce.user_service.mapper.AuthMapper;
import com.ecommerce.user_service.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil util;
    private AuthMapper authMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserResponseDto register(UserCreateRequestDto dto) {
       if (userRepository.existsByEmail(dto.getEmail())){
           throw new ResourceAlreadyExistsException("Email already registered");
       }
       User user=userMapper.toEntity(dto);
      User saved= userRepository.save(user);
      return userMapper.toDto(saved);
    }

    @Override
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Invalid Credential"));
        boolean matches=passwordEncoder.matches(loginRequestDto.getPassword(),user.getPassword());
         if(!matches) {
             throw new ResourceNotFoundException("Invalid credentials ...");
         }
        String token = util.generateToken(user.getId(), user.getEmail(), user.getRoles());
        return authMapper.toLoginResponse(user, token);
    }

    @Override
    public UserResponseDto getById(Integer id) {
        User user=userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found ."+id));

        return userMapper.toDto(user);
    }
}
