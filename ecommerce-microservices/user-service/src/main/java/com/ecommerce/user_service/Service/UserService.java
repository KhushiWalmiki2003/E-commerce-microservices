package com.ecommerce.user_service.Service;

import com.ecommerce.user_service.dto.LoginRequestDto;
import com.ecommerce.user_service.dto.LoginResponseDto;
import com.ecommerce.user_service.dto.UserCreateRequestDto;
import com.ecommerce.user_service.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserCreateRequestDto dto);
   public LoginResponseDto login(LoginRequestDto loginRequestDto);
    UserResponseDto getById(Integer id);
}
