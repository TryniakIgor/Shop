package com.example.shop.controller;

import com.example.shop.model.Department;
import com.example.shop.model.User;
import com.example.shop.service.DepartmentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getDepartments() {
        return ResponseEntity.ok().body(departmentService.getAllDepartmets());
    }
    @GetMapping("/department/users/{nameDepartment}")
    public ResponseEntity<List<User>> getUsers(@PathVariable String nameDepartment) {
        return ResponseEntity.ok().body(departmentService.getAllUser(nameDepartment));
    }
    @PostMapping("/department/save")
    public ResponseEntity<Department> getUsers(Department department) {
        return ResponseEntity.ok().body(departmentService.save(department));
    }
    @GetMapping("/departments/{numberUsersInDepartment}")
    public ResponseEntity<List<Department>> moreTnanUsersInDepariment(@PathVariable int numberUsersInDepartment) {
        return ResponseEntity.ok().body(departmentService.moreTnanUsersInDepariment(numberUsersInDepartment));
    }
}
