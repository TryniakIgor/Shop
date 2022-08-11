package com.example.shop.controller;

import com.example.shop.dto.UserDTO;
import com.example.shop.model.User;
import com.example.shop.repository.UserRepo;
import com.example.shop.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${url}")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    private final UserRepo userRepo;

    @GetMapping("/user")
    public ResponseEntity<Map<String, Object>> getUsers(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "3") int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<User> pageTuts = userRepo.findAll(paging);

        Map<String, Object> response = new HashMap<>();
        response.put("users", userService.getUsers(paging));
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/user/{userName}")
    public ResponseEntity<UserDTO> findByName(@PathVariable String userName) {
        return ResponseEntity.ok().body(userService.getUser(userName));
    }

    @PutMapping("/user/{userName}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userName, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(userName, user));
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> saveUsers(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @DeleteMapping("/user/{userName}")
    public ResponseEntity<?> deleteByName(@PathVariable String userName) {
        userService.deleteUser(userName);
        return  ResponseEntity.noContent().build();
    }

    /**
     * return all users who work in departments of the @param location
     */
    @GetMapping("/users_by_location/{location}")
    public ResponseEntity<List<UserDTO>> getUsersByLocation(@PathVariable String location) {
        return ResponseEntity.ok().body(userService.findUserByLocation(location));
    }

    /**
     * return all users who are older than @param age
     */
    @GetMapping("/more_than_age/{age}")
    public ResponseEntity<List<UserDTO>> getUsersByLocation(@PathVariable int age) {
        return ResponseEntity.ok().body(userService.moreThanAge(age));
    }


}





