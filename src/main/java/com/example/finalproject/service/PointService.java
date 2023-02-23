package com.example.finalproject.service;

import com.example.finalproject.apiException.ApiException;
import com.example.finalproject.model.Point;
import com.example.finalproject.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;


    //////////////////////////////////////////////////
    //crud here
    public List<Point> getAll(){
        return pointRepository.findAll();
    }

    public Point getById(Integer id){
        Point point = pointRepository.findPointById(id);
        if (point == null) {
            throw new ApiException("point not found");
        }
        return point;
    }

    public void add(Point point){
        pointRepository.save(point);
    }

    public void update(Point newPoint,Integer id) {
        Point point= pointRepository.findPointById(id);
        if(point == null){
            throw new ApiException("Point ID not found");
        }
       point.setPoints(newPoint.getPoints());
        pointRepository.save(point);
    }

    public void delete(Integer id) {
        Point point= pointRepository.findPointById(id);
        if(point == null){
            throw new ApiException("Point ID not found");
        }
        pointRepository.delete(point);
    }
    ///////////////////////////////////////////////////
    // assign here

}
