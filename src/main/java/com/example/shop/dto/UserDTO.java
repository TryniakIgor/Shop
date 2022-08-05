package com.example.shop.dto;

import com.example.shop.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collection;
@Data
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String name;
    private String userName;
    private int age;

}
