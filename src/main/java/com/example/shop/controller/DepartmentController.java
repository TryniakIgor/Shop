package com.example.shop.controller;

import com.example.shop.dto.DepartmentDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.model.Department;
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
    public ResponseEntity<List<DepartmentDTO>> getDepartments() {
        return ResponseEntity.ok().body(departmentService.getAllDepartmets());
    }

    @GetMapping("/department/users/{nameDepartment}")
    public ResponseEntity<List<UserDTO>> getUsers(@PathVariable String nameDepartment) {
        return ResponseEntity.ok().body(departmentService.getAllUser(nameDepartment));
    }

    @PostMapping("/department")
    public ResponseEntity<DepartmentDTO> getUsers(@RequestBody Department department) {
        return ResponseEntity.ok().body(departmentService.save(department));
    }

    @GetMapping("/department/{name}")
    public ResponseEntity<DepartmentDTO> getDepartmentByName(@PathVariable String name) {
        return ResponseEntity.ok().body(departmentService.getDepartment(name));
    }

    @PutMapping("/department/{name}")
    public ResponseEntity<DepartmentDTO> updateUser(@PathVariable String name, @RequestBody Department department) {
        return ResponseEntity.ok().body(departmentService.updateDepartment(name, department));
    }

    @DeleteMapping("/department/{name}")
    public void deleteByName(@PathVariable String name) {
        departmentService.deleteDepartment(name);
    }

    /**
     * return departments where number of users is greater than @param numberUsersInDepartment
     */
    @GetMapping("/departments/{numberUsersInDepartment}")
    public ResponseEntity<List<DepartmentDTO>> moreTnanUsersInDepariment(@PathVariable int numberUsersInDepartment) {
        return ResponseEntity.ok().body(departmentService.moreTnanUsersInDepariment(numberUsersInDepartment));
    }
}
