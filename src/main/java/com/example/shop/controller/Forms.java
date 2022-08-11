package com.example.shop.controller;

import lombok.Data;


public class Forms{

}
@Data
class UserToDepartmentForm {
    private String userName;
    private String departmentName;
}
@Data
 class RoleToUserForm {
    private String userName;
    private String roleName;
}