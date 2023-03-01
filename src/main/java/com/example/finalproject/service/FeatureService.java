package com.example.finalproject.service;

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

    public Feature getFeatureByBranchId(Integer id){
        Feature feature = featureRepository.findFeatureByBranchId(id);
        if (feature == null) {
            throw new ApiException("feature not found");
        }
        return feature;
    }
    /////////////////
    public List<Feature> getAllFeaturesByBranchId(Integer branchId){
        List<Feature>  feature = featureRepository.findAllFeatureByBranchId(branchId);
        if (feature == null) {
            throw new ApiException("feature not found");
        }
        return feature;
    }


    public void update(Feature newFeature,Integer id,Integer auth) {
        Feature feature= featureRepository.findFeatureById(id);
        if(feature == null){
            throw new ApiException("Feature ID not found");
        }else if(feature.getBranch().getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority!");
        }
        feature.setName(newFeature.getName());
        feature.setDescription(newFeature.getDescription());
        featureRepository.save(feature);
    }

    public void delete(Integer id,Integer auth) {
        Feature feature= featureRepository.findFeatureById(id);
        if(feature == null){
            throw new ApiException("Feature ID not found");
        }else if(feature.getBranch().getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority!");
        }
        featureRepository.delete(feature);
    }
    ///////////////////////////////////////////////////
    // assign here

    public void assignFeatureToBranch(Feature feature, Integer branchId,Integer auth){
        Branch branch = branchRepository.findBranchById(branchId);
        if (branch == null ) {
            throw new ApiException("Branch ID not found");
        }
        else if(branch.getMerchant().getMyUser().getId() != auth){
            throw new ApiException("Sorry , You do not have the authority!");
        }

        feature.setBranch(branch);
        featureRepository.save(feature);
    }
}
