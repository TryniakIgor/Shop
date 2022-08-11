package com.example.shop.service;

import com.example.shop.dto.UserDTO;
import com.example.shop.exeption.EntityAlreadyExist;
import com.example.shop.exeption.ResourseNotFoundExeption;
import com.example.shop.mapper.UserMapper;
import com.example.shop.model.Location;
import com.example.shop.model.User;
import com.example.shop.repository.RoleRepo;
import com.example.shop.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO saveUser(User user) {
        log.info("Saving new user {} to DB", user.getName());

        Optional<User> existingUser = Optional.ofNullable(userRepo.findByUserNameAndIsDeletedIsFalse(user.getUserName()));
        existingUser.ifPresentOrElse(
                (value) -> {
                    throw new EntityAlreadyExist("User", user.getName());
                }, () -> userRepo.save(user)
        );
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return UserMapper.toDTO(userRepo.save(user));
    }

    @Override
    public UserDTO getUser(String userName) {
        log.info("Fetching user {}", userName);
        User user = userRepo.findByUserNameAndIsDeletedIsFalse(userName);
        if (user != null)
            return UserMapper.toDTO(user);
        else throw new ResourseNotFoundExeption("User", userName);
    }

    @Override
    public List<UserDTO> getUsers(Pageable pageable) {
        log.info("Fetching all users");
        return userRepo.findAll(pageable).stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> findUserByLocation(String location) {
        log.info("Fetching users by location {} ", location);
        Location location1 = Location.valueOf(location.toUpperCase());
        String isPresent = null;
        for (Location loc : Location.values()) {
            if (loc.toString().equals(location.toUpperCase())) {
                isPresent = loc.toString();
                break;
            }
        }
        Optional.ofNullable(isPresent).orElseThrow(() -> new ResourseNotFoundExeption("Location", location));
        return userRepo.findByLocation(location1).stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> moreThanAge(int age) {
        log.info("Fetching users older than {} ", age);
        return userRepo.findAll(PageRequest.of(0, 10)).stream().filter(user -> user.getAge() > age).map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(String userName, User user) {
        log.info("Update user {} ", userName);

        User oldUser = Optional.ofNullable(userRepo.findByUserNameAndIsDeletedIsFalse(userName)).orElseThrow(() -> new ResourseNotFoundExeption("User", userName));
        oldUser.setId(user.getId());
        oldUser.setName(user.getName());
        oldUser.setPassword(user.getPassword());
        oldUser.setAge(user.getAge());

        return UserMapper.toDTO(oldUser);
    }

    @Override
    public void deleteUser(String userName) {
        log.info("Change isDeleted user {} with true", userName);
        Optional.ofNullable(userRepo.findByUserNameAndIsDeletedIsFalse(userName)).orElseThrow(() -> new ResourseNotFoundExeption("User", userName));
        userRepo.markAsDeleted(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserNameAndIsDeletedIsFalse(username);
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
