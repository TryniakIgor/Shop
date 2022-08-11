package com.example.shop.controller;

import com.example.shop.dto.UserDTO;
import com.example.shop.model.User;
import com.example.shop.repository.UserRepo;
import com.example.shop.service.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = UserController.class, useDefaultFilters = false, includeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UserController.class)})
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @MockBean
    UserServiceImpl userServiceimpl;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    UserRepo userRepo;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private User testUser() {
        return User.builder()
                .id(1L)
                .name("Luk")
                .userName("luk111")
                .password("1111")
                .age(22)
                .roles(new ArrayList<>())
                .isDeleted(false)
                .build();
    }
    private UserDTO testUserDTO() {
        return UserDTO.builder()
                .id(1L)
                .name("Luk")
                .userName("luk111")
                .age(22)
                .build();
    }
    @Test
    void getUsers() throws Exception {
        List<UserDTO> usersDTO = new ArrayList<>();
        usersDTO.add(testUserDTO());
        usersDTO.add(new UserDTO(2L, "Gina", " gina", 28));

        List<User> users1 = new ArrayList<>();
        users1.add(testUser());
        users1.add(new User(2L, "Gina", " gina", "0000", 55, new ArrayList<>(), false));

        Pageable paging = PageRequest.of(0, 3);
        when(userRepo.findAll(paging)).thenReturn((new PageImpl<>(users1)));
        Page<User> pageTuts = userRepo.findAll(paging);
        Map<String, Object> response = new HashMap<>();

        when(response.put("users", userServiceimpl.getUsers(paging))).thenReturn(usersDTO);
        response.put("users", userServiceimpl.getUsers(paging));
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());

        MvcResult mvcResult = mockMvc.perform(get("/api/user?page=0&size=3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalItems", is(2) ))
                .andExpect(jsonPath("$.currentPage", is(0) ))
                .andExpect(jsonPath("$.users.[0].name", is("Luk") ))
                .andExpect(jsonPath("$.users.[0].age", is(22) ))

                .andReturn();

    }

    @Test
    void findByName() throws Exception {
        when(userServiceimpl.getUser("luk111")).thenReturn(testUserDTO());

        MvcResult mvcResult = mockMvc.perform(get("/api/user/luk111").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Luk")))
                .andReturn();

        assertEquals(objectMapper.writeValueAsString(testUserDTO()), mvcResult.getResponse().getContentAsString());
    }

    @Test
    void saveRoles() throws Exception {
        when(userServiceimpl.saveUser(testUser())).thenReturn(testUserDTO());

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/api/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(testUser()));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Luk")));
    }
}