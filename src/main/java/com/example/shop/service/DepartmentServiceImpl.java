package com.example.shop.service;

import com.example.shop.dto.DepartmentDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.mapper.DepatmentMapper;
import com.example.shop.mapper.UserMapper;
import com.example.shop.model.Department;
import com.example.shop.model.User;
import com.example.shop.repository.DepartmentRepo;
import com.example.shop.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    public DepartmentDTO save(Department department) {
        log.info("Saving new department {} to DB", department.getName());
        return DepatmentMapper.toDTO(departmentRepo.save(department));
    }

    @Override
    public DepartmentDTO getDepartment(String name) {

        return DepatmentMapper.toDTO(departmentRepo.findByDepartmentName(name));
    }

    @Override
    public List<UserDTO> getAllUser(String departmentName) {
        Department department = departmentRepo.findByDepartmentName(departmentName);
        return department.getUsers().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<DepartmentDTO> getAllDepartmets() {
        return departmentRepo.findAll().stream().map(DepatmentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void addUserToDepartment(String userName, String departmentName) {
        log.info("Adding user {} to department {} ", userName, departmentName);
        User user = userRepo.findByUserName(userName);
        Department department = departmentRepo.findByDepartmentName(departmentName);
        department.getUsers().add(user);
    }

    @Override
    public List<DepartmentDTO> moreTnanUsersInDepariment(int number) {

        return departmentRepo.findAll().stream().filter(department -> department.getUsers().size() > number).map(DepatmentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO updateDepartment(String name, Department department) {
        log.info("Update department {} ", name);

        for (int i = 0; i < getAllDepartmets().size(); i++) {
            Department department1 = departmentRepo.findAll().get(i);
            if (department1.getName().equals(name)) {
                department.setId(department1.getId());
                departmentRepo.deleteByName(name);
                departmentRepo.save(department);
            } else throw new EntityNotFoundException("Department " + name + " doesn't exist");

        }

        return DepatmentMapper.toDTO(department);
    }

    @Override
    public void deleteDepartment(String name) {
        log.info("Change isDeleted department {} with true", name);
        departmentRepo.markAsDeleted(name);
    }
}
