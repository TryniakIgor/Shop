package com.example.shop.service;

import com.example.shop.dto.UserDTO;
import com.example.shop.model.Role;
import com.example.shop.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    UserDTO saveUser(User user);

    UserDTO getUser(String userName);

    void deleteUser(String userName);

    UserDTO updateUser(String userName, User user);

    List<UserDTO> getUsers(Pageable pageable);

    List<UserDTO> findUserByLocation(String location);

    List<UserDTO> moreThanAge(int age);


}
