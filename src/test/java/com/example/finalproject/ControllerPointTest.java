package com.example.finalproject;

import com.example.finalproject.controller.PointController;
import com.example.finalproject.service.PointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.geo.Point;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.RequestEntity.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = PointController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ControllerPointTest {
    @MockBean
    PointService pointService;

    @Autowired
    MockMvc mockMvc;

    com.example.finalproject.model.Point point;

    List<Point> point1;

    @BeforeEach
    void setUp() {
        point = new com.example.finalproject.model.Point(1, 20.0, null, null);
    }

    @Test
    public void deleteaaUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/point/delete/{id}", point.getId()))
                .andExpect(status().isOk());
    }
}
