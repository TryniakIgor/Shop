package com.example.shop.service;

import com.example.shop.dto.DepartmentDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.model.Department;

import java.util.List;

public interface DepartmentService {
    DepartmentDTO save(Department department);

    DepartmentDTO getDepartment(String name);

    List<DepartmentDTO> getAllDepartmets();

    List<UserDTO> getAllUserInDepartment(String departmentName);

    List<DepartmentDTO> moreTnanUsersInDepariment(int number);

    DepartmentDTO updateDepartment(String name, Department department);

    void deleteDepartment(String name);

    void addUserToDepartment(String userName, String departmentName);
}
