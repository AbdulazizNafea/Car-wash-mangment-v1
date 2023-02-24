package com.example.finalproject.service;

import com.example.finalproject.DTO.CustomerDTO;
import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.BranchRepository;
import com.example.finalproject.repository.FeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeatureService {

    private final FeatureRepository featureRepository;
    private final BranchRepository branchRepository;


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

//    public void add(Feature feature){
//        featureRepository.save(feature);
//    }

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

    public void assignFeatureToBranch(Feature feature, Integer branchId){
        Branch branch = branchRepository.findBranchById(branchId);
        if (branch == null ) {
            throw new ApiException("Branch ID not found");
        }
        if(!branch.getMerchant().getMyUser().getRole().equalsIgnoreCase("merchant")){
            throw new ApiException("Your role isn't merchant");
        }
        feature.setBranch(branch);
        featureRepository.save(feature);
    }
}
