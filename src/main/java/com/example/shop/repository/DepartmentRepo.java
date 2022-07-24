package com.example.shop.repository;

import com.example.shop.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
    Department findByName(String name);
}
