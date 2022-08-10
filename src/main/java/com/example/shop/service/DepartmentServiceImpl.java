package com.example.shop.service;

import com.example.shop.dto.DepartmentDTO;
import com.example.shop.dto.UserDTO;
import com.example.shop.exeption.EntityAlreadyExist;
import com.example.shop.exeption.ResourseNotFoundExeption;
import com.example.shop.mapper.DepatmentMapper;
import com.example.shop.mapper.UserMapper;
import com.example.shop.model.Department;
import com.example.shop.model.User;
import com.example.shop.repository.DepartmentRepo;
import com.example.shop.repository.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepo departmentRepo;
    @Autowired
    UserRepo userRepo;

    @Override
    public DepartmentDTO save(Department department) {
        log.info("Saving new department {} to DB", department.getName());

        Optional<Department> existingUser = Optional.ofNullable(departmentRepo.findByDepartmentName(department.getName()));
        existingUser.ifPresentOrElse(
                (value) -> {
                    throw new EntityAlreadyExist("Department", department.getName());
                }, () -> departmentRepo.save(department)
        );
        return DepatmentMapper.toDTO(departmentRepo.save(department));

    }

    @Override
    public DepartmentDTO getDepartment(String name) {
        return DepatmentMapper.toDTO(Optional.ofNullable(departmentRepo.findByDepartmentName(name)).orElseThrow(() -> new ResourseNotFoundExeption("Department", name)));
    }

    @Override
    public List<UserDTO> getAllUserInDepartment(String departmentName) {
        Department department = Optional.ofNullable(departmentRepo.findByDepartmentName(departmentName)).orElseThrow(() -> new ResourseNotFoundExeption("Department", departmentName));
        return department.getUsers().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<DepartmentDTO> getAllDepartmets() {
        return departmentRepo.findAll().stream().map(DepatmentMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void addUserToDepartment(String userName, String departmentName) {
        log.info("Adding user {} to department {} ", userName, departmentName);
        User user = Optional.ofNullable(userRepo.findByUserName(userName)).orElseThrow(() -> new ResourseNotFoundExeption("User", userName));
        Department department = Optional.ofNullable(departmentRepo.findByDepartmentName(departmentName)).orElseThrow(() -> new ResourseNotFoundExeption("Department", departmentName));
        department.getUsers().add(user);
    }

    @Override
    public List<DepartmentDTO> moreTnanUsersInDepariment(int number) {

        return getAllDepartmets().stream().filter(departmentDTO -> departmentDTO.getUsersDTO().size() > number).collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO updateDepartment(String name, Department department) {
        log.info("Update department {} ", name);
        Department oldDepartment = Optional.ofNullable(departmentRepo.findByDepartmentName(name)).orElseThrow(() -> new ResourseNotFoundExeption("Department", name));
        oldDepartment.setId(department.getId());
        oldDepartment.setLocation(department.getLocation());
        oldDepartment.setUsers(department.getUsers());

        return DepatmentMapper.toDTO(oldDepartment);
    }

    @Override
    public void deleteDepartment(String name) {
        log.info("Change isDeleted department {} with true", name);
        Optional.ofNullable(departmentRepo.findByDepartmentName(name)).orElseThrow(() -> new ResourseNotFoundExeption("Department", name));
        departmentRepo.markAsDeleted(name);
    }
}
