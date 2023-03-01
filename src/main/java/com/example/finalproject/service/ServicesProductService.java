package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.*;
import com.example.finalproject.repository.BillRepository;
import com.example.finalproject.repository.BranchRepository;
import com.example.finalproject.repository.MyUserRepository;
import com.example.finalproject.repository.ServicesProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicesProductService {

    private final ServicesProductRepository servicesProductRepository;
    private final BranchRepository branchRepository;
    private final BillRepository billRepository;
    private final MyUserRepository myUserRepository;



    //////////////////////////////////////////////////
    //crud here
    public List<ServicesProduct> getAll(){
        return servicesProductRepository.findAll();
    }

    //all role
    public ServicesProduct getById(Integer id, Integer auth){
        MyUser myUser = myUserRepository.findMyUserById(auth);
        ServicesProduct servicesProduct = servicesProductRepository.findServicesProductById(id);
        if (servicesProduct == null) {
            throw new ApiException("service Product not found");
        }else if (servicesProduct.getBranch().getMerchant().getMyUser().getId() != auth) {
            throw new ApiException("you dont hava access");
        }
        return servicesProduct;
    }

    public List<ServicesProduct> getByBranchId(Integer branchId, Integer auth){
        Branch branch = branchRepository.findBranchById(branchId);
        List<ServicesProduct> servicesProduct = servicesProductRepository.findAllServicesProductByBranchId(branchId);
        if (servicesProduct == null) {
            throw new ApiException("service Product not found");
        }else if (branch.getMerchant().getMyUser().getId() != auth) {
            throw new ApiException("you dont hava access");
        }
        return servicesProduct;
    }

//    public void add(ServicesProduct servicesProduct){
//        servicesProductRepository.save(servicesProduct);
//    }

    //Merchant Role
    public void update(ServicesProduct newServicesProduct,Integer id , Integer auth) {
        ServicesProduct servicesProduct= servicesProductRepository.findServicesProductById(id);
        if(servicesProduct == null){
            throw new ApiException("Service Product ID not found");
        }else if(servicesProduct.getBranch().getMerchant().getMyUser().getId() != auth){
            throw new ApiException("your not authorize to update this services");
        }
        servicesProduct.setName(newServicesProduct.getName());
        servicesProduct.setDescription(newServicesProduct.getDescription());
        servicesProduct.setPoint(newServicesProduct.getPoint());
        servicesProduct.setPrice(newServicesProduct.getPrice());
        servicesProduct.setTotalPoints(newServicesProduct.getTotalPoints());
        servicesProductRepository.save(servicesProduct);
    }

    //Merchant Role
    public void delete(Integer id, Integer auth) {
        ServicesProduct servicesProduct= servicesProductRepository.findServicesProductById(id);
        if(servicesProduct == null){
            throw new ApiException("service Product ID not found");
        }else if(servicesProduct.getBranch().getMerchant().getMyUser().getId() != auth){
            throw new ApiException("your not authorize to delete this services");
        }
        servicesProductRepository.delete(servicesProduct);
    }
    ///////////////////////////////////////////////////
    // assign here

    //Merchant Role
    public void addServicesToBranch(ServicesProduct sp, Integer branchId, Integer auth){
        Branch branch = branchRepository.findBranchById(branchId);
        if (branch == null ) {
            throw new ApiException("Branch ID not found");
        }
        if(branch.getMerchant().getMyUser().getId() != auth){
            throw new ApiException("your not authorize to Assign this services to Branch");
        }
        sp.setBranch(branch);
        servicesProductRepository.save(sp);
    }


}
