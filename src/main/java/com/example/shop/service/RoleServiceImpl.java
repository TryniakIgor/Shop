package com.example.shop.service;

import com.example.shop.exeption.ResourseNotFoundExeption;
import com.example.shop.model.Role;
import com.example.shop.model.User;
import com.example.shop.repository.RoleRepo;
import com.example.shop.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to DB", role.getName());

        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        log.info("Adding role {} to user {} ", roleName, userName);
        User user = Optional.ofNullable(userRepo.findByUserNameAndIsDeletedIsFalse(userName)).orElseThrow(() -> new ResourseNotFoundExeption("User", userName));
        Role role = Optional.ofNullable(roleRepo.findByName(roleName)).orElseThrow(() -> new ResourseNotFoundExeption("Role", roleName));
        user.getRoles().add(role);
    }
}
