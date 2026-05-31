package com.app.controller;

import com.app.entity.cars.Brand;
import com.app.entity.cars.Car;
import com.app.repository.BrandRepository;
import com.app.repository.CarRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/search-car")
public class SearchCarController {

    private BrandRepository brandRepository;
    private CarRepository carRepository;

    public SearchCarController(BrandRepository brandRepository, CarRepository carRepository) {
        this.brandRepository = brandRepository;
        this.carRepository = carRepository;
    }

    // http://localhost:8080/api/v1/search-car/cars?param=honda  this is the open url this is not secure , without login we can access it
    @GetMapping("/cars")
    public List<Car> searchCars(
            @RequestParam String param
    ){
        return carRepository.SearchCar(param);
    }
}
