package com.example.finalproject;

import com.example.finalproject.model.Customer;
import com.example.finalproject.model.MyUser;
import com.example.finalproject.model.Point;
import com.example.finalproject.repository.PointRepository;
import com.example.finalproject.service.PointService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PointServiceTest {
    @InjectMocks
    PointService pointService;
    @Mock
    PointRepository pointRepository;

    Point point1;

    @BeforeEach
    void setUp(){
        point1 = new Point(null, 1.2);
    }

    @Test
    public void getPoint() {
        pointRepository.save(point1);
        when(pointRepository.findPointById(point1.getId())).thenReturn(point1);
        Point point = pointRepository.findPointById(point1.getId());
        //Note//read above note to understand
        Assertions.assertThat(point1).isEqualTo(point);

        verify(pointRepository, times(1)).findPointById(point1.getId());
    }
}
