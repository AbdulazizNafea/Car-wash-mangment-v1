package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Feature;
import com.example.finalproject.model.Point;
import com.example.finalproject.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;


    //////////////////////////////////////////////////
    //crud here
    public List<Feature> getAll(){
        return featureRepository.findAll();
    }

    public Feature getById(Integer id){
        Feature feature = featureRepository.findFeatureById(id);
        if (feature == null) {
            throw new ApiException("feature not found");
        }
        return feature;
    }

    public void add(Feature feature){
        featureRepository.save(feature);
    }

    public void update(Feature newFeature,Integer id) {
        Feature feature= featureRepository.findFeatureById(id);
        if(feature == null){
            throw new ApiException("Feature ID not found");
        }
        feature.setName(newFeature.getName());
        feature.setDescription(newFeature.getDescription());
        featureRepository.save(feature);
    }

    public void delete(Integer id) {
        Feature feature= featureRepository.findFeatureById(id);
        if(feature == null){
            throw new ApiException("Feature ID not found");
        }
        featureRepository.delete(feature);
    }
    ///////////////////////////////////////////////////
    // assign here

}
