package com.example.shop.service;

import com.example.shop.model.Role;

public interface RoleService {
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
}
