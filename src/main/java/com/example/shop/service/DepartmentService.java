package com.example.shop.service;

import com.example.shop.model.Department;
import com.example.shop.model.User;

import java.util.List;

public interface DepartmentService {
    Department save (Department department);
    Department getDepartment(String name);
    List<Department> getAllDepartmets();
    List<User> getAllUser(String departmentName);
     void addUserToDepartment(String userName, String departmentName);
    List<Department> moreTnanUsersInDepariment(int number);
}
