package com.example.shop.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.shop.dto.UserDTO;
import com.example.shop.model.Role;
import com.example.shop.model.User;
import com.example.shop.repository.UserRepo;
import com.example.shop.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;
    private final UserRepo userRepo;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @GetMapping("user/{userName}")
    public ResponseEntity<UserDTO> findByName(@PathVariable String userName) {
        return ResponseEntity.ok().body(userService.getUser(userName));
    }

    @PutMapping("user/{userName}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String userName, @RequestBody User user) {
        return ResponseEntity.ok().body(userService.updateUser(userName, user));
    }

    @PostMapping("/user")
    public ResponseEntity<UserDTO> saveUsers(@RequestBody User user) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role")
    public ResponseEntity<Role> saveRoles(@RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @DeleteMapping("user/{userName}")
    public void deleteByName(@PathVariable String userName) {
        userService.deleteUser(userName);    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<Role> addRoleToUser(@RequestBody RoleToUserForm form) {
        userService.addRoleToUser(form.getUserName(), form.getRoleName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/addToDepartment")
    public ResponseEntity<Role> addUserToDepartment(@RequestBody UserToDepartmentForm form) {
        userService.addUserToDepartment(form.getUserName(), form.getDepartmentName());
        return ResponseEntity.ok().build();
    }

    /**
    * return all users who work in departments of the @param location
     */
    @GetMapping("/usersByLocation/{location}")
    public ResponseEntity<List<UserDTO>> getUsersByLocation(@PathVariable String location) {
        return ResponseEntity.ok().body(userService.findUserByLocation(location));
    }

    /**
     * return all users who are older than @param age
     */
    @GetMapping("/moreThanAge/{age}")
    public ResponseEntity<List<UserDTO>> getUsersByLocation(@PathVariable int age) {
        return ResponseEntity.ok().body(userService.moreThanAge(age));
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String userName = decodedJWT.getSubject();
                User user = userRepo.findByUserName(userName);
                String access_token = JWT.create()
                        .withSubject(user.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 20 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_mesage", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}

@Data
class RoleToUserForm {
    private String userName;
    private String roleName;
}

@Data
class UserToDepartmentForm {
    private String userName;
    private String departmentName;
}

