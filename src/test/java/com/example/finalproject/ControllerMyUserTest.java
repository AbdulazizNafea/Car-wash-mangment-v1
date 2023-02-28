package com.example.finalproject;

import com.example.finalproject.controller.MyUserController;
import com.example.finalproject.model.Merchant;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.service.MyUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MyUserController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ControllerMyUserTest {
    @MockBean
    MyUserService myUserService;

    @Autowired
    MockMvc mockMvc;

    MyUser myUser;
    Merchant merchant;

    List<MyUser> myUsers1, myUsers2;

    @BeforeEach
    void setUp() {
        merchant = new Merchant(3, "company", "34567", myUser, null, null, null);
        myUser = new MyUser(5, "abdulaziz", "123", "aziz@email.com", "0595999590",null, "Customer", null, null);

        myUsers1 = Arrays.asList(myUser);
    }

    @Test
    public void GetAllUsers() throws Exception {
        Mockito.when(myUserService.getAll()).thenReturn(myUsers1);
        mockMvc.perform(get("/api/v1/user/getAll"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
    }

    @Test
    public void AddUser() throws Exception {
        mockMvc.perform(post("/api/v1/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content( new ObjectMapper().writeValueAsString(myUser)))
                .andExpect(status().is(201));
    }

    @Test
    public void GetUser() throws Exception {
        Mockito.when(myUserService.getMyUser(myUser.getId())).thenReturn(myUser);
        mockMvc.perform(get("/api/v1/user/getMyUser"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteUser() throws Exception {
            mockMvc.perform(delete("/api/v1/user/delete/{id}",myUser.getId()))
                    .andExpect(status().isOk());
    }


}
