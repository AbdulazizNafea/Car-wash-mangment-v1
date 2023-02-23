package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.ServicesProduct;
import com.example.finalproject.repository.ServicesProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicesProductService {

    private final ServicesProductRepository servicesProductRepository;


    //////////////////////////////////////////////////
    //crud here
    public List<ServicesProduct> getAll(){
        return servicesProductRepository.findAll();
    }

    public ServicesProduct getById(Integer id){
        ServicesProduct servicesProduct = servicesProductRepository.findServicesProductById(id);
        if (servicesProduct == null) {
            throw new ApiException("service Product not found");
        }
        return servicesProduct;
    }

    public void add(ServicesProduct servicesProduct){
        servicesProductRepository.save(servicesProduct);
    }

    public void update(ServicesProduct newServicesProduct,Integer id) {
        ServicesProduct servicesProduct= servicesProductRepository.findServicesProductById(id);
        if(servicesProduct == null){
            throw new ApiException("Service Product ID not found");
        }

        servicesProduct.setName(newServicesProduct.getName());
        servicesProduct.setDescription(newServicesProduct.getDescription());
        servicesProduct.setPoint(newServicesProduct.getPoint());
        servicesProduct.setPrice(newServicesProduct.getPrice());
        servicesProduct.setTotalPoints(newServicesProduct.getTotalPoints());
        servicesProductRepository.save(servicesProduct);
    }

    public void delete(Integer id) {
        ServicesProduct servicesProduct= servicesProductRepository.findServicesProductById(id);
        if(servicesProduct == null){
            throw new ApiException("service Product ID not found");
        }
        servicesProductRepository.delete(servicesProduct);
    }
    ///////////////////////////////////////////////////
    // assign here

}
