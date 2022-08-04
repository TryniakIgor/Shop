package com.example.shop.dto;

import com.example.shop.model.Location;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
public class DepartmentDTO {

    private Long id;

    private String name;

    private Location location;

    private List<UserDTO> usersDTO = new ArrayList<>();
}
