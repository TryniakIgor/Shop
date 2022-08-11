package com.example.shop.repository;

import com.example.shop.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentRepo extends JpaRepository<Department, Long> {


    @Query("select d from Department d where d.name =:name and  d.isDeleted = false ")
    Department findByDepartmentName(String name);


    @Override
    @Query("select d from Department d where d.isDeleted = false ")
    List<Department> findAll();

    @Modifying
    @Query ("UPDATE Department d SET d.isDeleted = true WHERE d.name =:name")
    void markAsDeleted(String name);



}
