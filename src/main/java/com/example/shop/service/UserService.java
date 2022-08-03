package com.example.shop.service;

import com.example.shop.dto.UserDTO;
import com.example.shop.model.Role;
import com.example.shop.model.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    UserDTO getUser(String userName);
    UserDTO deleteUser(String userName);
    List<UserDTO> getUsers();
    List<UserDTO> findUserByLocation (String location);
    List<UserDTO> moreThanAge (int age);


}
