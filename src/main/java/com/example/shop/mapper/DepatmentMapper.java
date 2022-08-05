package com.example.shop.mapper;

import com.example.shop.dto.DepartmentDTO;
import com.example.shop.model.Department;

import java.util.stream.Collectors;

public class DepatmentMapper {
    public static DepartmentDTO toDTO(Department department){
        return DepartmentDTO.builder()
                .id(department.getId())
                .name(department.getName())
                .location(department.getLocation())
                .build();
    }
}
