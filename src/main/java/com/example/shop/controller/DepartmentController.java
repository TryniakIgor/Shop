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
@RequestMapping("${url}")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class DepartmentController {
    private final DepartmentService departmentService;

    @GetMapping("/department")
    public ResponseEntity<List<DepartmentDTO>> getDepartments() {
        return ResponseEntity.ok().body(departmentService.getAllDepartmets());
    }

    @GetMapping("/department/users/{nameDepartment}")
    public ResponseEntity<List<UserDTO>> getAllUserInDepartment(@PathVariable String nameDepartment) {
        return ResponseEntity.ok().body(departmentService.getAllUserInDepartment(nameDepartment));
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
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable String name, @RequestBody Department department) {
        return ResponseEntity.ok().body(departmentService.updateDepartment(name, department));
    }

    @DeleteMapping("/department/{name}")
    public ResponseEntity deleteByName(@PathVariable String name) {
        departmentService.deleteDepartment(name);
        return  ResponseEntity.noContent().build();
    }

    @PostMapping("/department/add_user_to_department")
    public ResponseEntity<DepartmentDTO> addUserToDepartment(@RequestBody UserToDepartmentForm form) {
        departmentService.addUserToDepartment(form.getUserName(), form.getDepartmentName());
        return ResponseEntity.ok().build();
    }

    /**
     * return departments where number of users is greater than @param numberUsersInDepartment
     */
    @GetMapping("/department/number_user_in_department/{numberUsersInDepartment}")
    public ResponseEntity<List<DepartmentDTO>> moreTnanUsersInDepariment(@PathVariable int numberUsersInDepartment) {
        return ResponseEntity.ok().body(departmentService.moreTnanUsersInDepariment(numberUsersInDepartment));
    }
}

