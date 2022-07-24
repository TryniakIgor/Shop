package com.example.shop.service;

import com.example.shop.model.Department;
import com.example.shop.model.User;
import com.example.shop.repository.DepartmentRepo;
import com.example.shop.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    UserRepo userRepo;
    @Override
    public Department save(Department department) {
        log.info("Saving new department {} to DB", department.getName());
        return departmentRepo.save(department);
    }

    @Override
    public Department getDepartment(String name) {
        return departmentRepo.findByName(name);
    }

    @Override
    public List<User> getAllUser(String departmentName) {
        Department department = departmentRepo.findByName(departmentName);
        return department.getUsers();
    }

    @Override
    public List<Department> getAllDepartmets() {
        return departmentRepo.findAll();
    }
    @Override
    public void addUserToDepartment(String userName, String departmentName) {
        log.info("Adding user {} to department {} ", userName, departmentName);
        User user = userRepo.findByUserName(userName);
        Department department = departmentRepo.findByName(departmentName);
        department.getUsers().add(user);

    }
    @Override
    public List<Department> moreTnanUsersInDepariment(int number) {

        return  departmentRepo.findAll().stream().filter(department ->department.getUsers().size()>number).collect(Collectors.toList());


    }
}
