package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@Data
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String name;
    private String userName;
    private int age;

}
