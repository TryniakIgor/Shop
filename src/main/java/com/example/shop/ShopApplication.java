package com.example.shop;

import com.example.shop.model.Department;
import com.example.shop.model.Location;
import com.example.shop.model.Role;
import com.example.shop.model.User;
import com.example.shop.service.DepartmentService;
import com.example.shop.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.PasswordAuthentication;
import java.util.ArrayList;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
//	@Bean
//	CommandLineRunner run(UserService userService, DepartmentService departmentService){
//		return args ->{
//		userService.saveRole(new Role(null, "ADMIN"));
//		userService.saveRole(new Role(null, "MANAGER"));
//		userService.saveRole(new Role(null, "SUPER_ADMIN"));
//		userService.saveRole(new Role(null, "SELLER"));
//
//		userService.saveUser(new User(null, "John Dou", "john","1111",new ArrayList<>()));
//		userService.saveUser(new User(null, "Jack Rassel", "jack","1234",new ArrayList<>()));
//		userService.saveUser(new User(null, "Luk Skywocker", "luk","qwwer",new ArrayList<>()));
//		userService.saveUser(new User(null, "Jessica Alba", "jess","0000",new ArrayList<>()));
//
//		userService.addRoleToUser("john", "ADMIN");
//		userService.addRoleToUser("jack", "MANAGER");
//		userService.addRoleToUser("luk", "SELLER");
//		userService.addRoleToUser("jess", "SELLER");
//		userService.addRoleToUser("jess", "MANAGER");
//		userService.addRoleToUser("jess", "SUPER_ADMIN");
//
//		departmentService.save(new Department(null, "sales", Location.LVIV, new ArrayList<>()));
//		departmentService.save(new Department(null, "delivery", Location.LVIV, new ArrayList<>()));
//		departmentService.save(new Department(null, "logistics", Location.LVIV, new ArrayList<>()));
//
//		departmentService.addUserToDepartment("john", "sales" );
//		departmentService.addUserToDepartment("luk", "sales" );
//		departmentService.addUserToDepartment("john", "delivery" );
//
//		};
//	}

}
