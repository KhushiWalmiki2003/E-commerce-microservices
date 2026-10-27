package com.ecommerce.user_service.mapper;

import com.ecommerce.user_service.dto.LoginRequestDto;
import com.ecommerce.user_service.dto.LoginResponseDto;
import com.ecommerce.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {
    public LoginResponseDto toLoginResponse(User user, String token){
        return new LoginResponseDto(token,"Bearer",user.getId(),user.getEmail(),user.getName());
    }

}
