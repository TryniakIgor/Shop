package com.example.shop.mapper;

import com.example.shop.dto.UserDTO;
import com.example.shop.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .userName(user.getUserName())
                .build();
    }

}
