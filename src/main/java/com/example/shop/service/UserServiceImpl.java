package com.example.shop.service;

import com.example.shop.dto.UserDTO;
import com.example.shop.exeption.ResourseNotFoundExeption;
import com.example.shop.mapper.UserMapper;
import com.example.shop.model.Department;
import com.example.shop.model.Location;
import com.example.shop.model.Role;
import com.example.shop.model.User;
import com.example.shop.repository.DepartmentRepo;
import com.example.shop.repository.RoleRepo;
import com.example.shop.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final DepartmentRepo departmentRepo;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO saveUser(User user) {
        log.info("Saving new user {} to DB", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDTO(userRepo.save(user));
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {} to DB", role.getName());
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        log.info("Adding role {} to user {} ", roleName, userName);
        User user = userRepo.findByUserName(userName);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    public void addUserToDepartment(String userName, String departmentName) {
        log.info("Adding user {} to department {} ", userName, departmentName);
        User user = userRepo.findByUserName(userName);
        Department department = departmentRepo.findByDepartmentName(departmentName);
        department.getUsers().add(user);
    }

    @Override
    public UserDTO getUser(String userName) {
        log.info("Fetching user {}", userName);
        User user = userRepo.findByUserName(userName);
        if (user != null)
            return UserMapper.toDTO(user);

         else throw new ResourseNotFoundExeption("User", userName);

    }

    @Override
    public List<UserDTO> getUsers() {
        log.info("Fetching all users");
        return userRepo.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findUserByLocation (String location){
        log.info("Fetching users by location{} ", location);
        Location location1 = Location.valueOf(location.toUpperCase());
        return userRepo.findByLocation(location1).stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> moreThanAge(int age){
        log.info("Fetching users older than {} ", age);
        return userRepo.findAll().stream().filter(user -> user.getAge() > age ).map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(String userName, User user) {
        log.info("Update user {} ", userName);

        for (int i = 0; i<getUsers().size(); i++){
            User user1 = userRepo.findAll().get(i);
            if (user1.getUserName().equals(userName)){
                user.setId(user1.getId());
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepo.deleteByName(userName);
                userRepo.save(user);
            }
            else throw new EntityNotFoundException("User with  user name "+ userName + " doesn't exist");

        }

        return UserMapper.toDTO(user);
    }

    @Override
    public void deleteUser(String userName) {
        log.info("Change isDeleted user {} with true", userName);
        userRepo.markAsDeleted(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserName(username);
        if (user == null) {
            log.error("User not found in DB");
            throw new UsernameNotFoundException("User not found in DB");
        } else {
            log.info("User found in DB:{}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities);
    }

}
