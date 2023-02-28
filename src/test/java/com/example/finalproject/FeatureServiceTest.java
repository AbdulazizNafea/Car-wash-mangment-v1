package com.example.finalproject;
import com.example.finalproject.model.Car;
import com.example.finalproject.model.Feature;
import com.example.finalproject.repository.FeatureRepository;
import com.example.finalproject.service.FeatureService;
import org.junit.jupiter.api.BeforeEach;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class FeatureServiceTest {
    @InjectMocks
    FeatureService featureService;

    @Mock
    FeatureRepository featureRepository;

    Feature feature1;

    @BeforeEach
    void setUp(){
        feature1 = new Feature(null, "coffee", "a place for coffee");
    }
    @Test
    public void getFeature() {
        featureRepository.save(feature1);
        when(featureRepository.findFeatureById(feature1.getId())).thenReturn(feature1);
        Feature feature = featureRepository.findFeatureById(feature1.getId());
        //Note//read above note to understand
        Assertions.assertThat(feature1).isEqualTo(feature);

        verify(featureRepository, times(1)).findFeatureById(feature1.getId());
    }

}
