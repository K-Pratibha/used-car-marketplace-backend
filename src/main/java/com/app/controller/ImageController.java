package com.app.controller;

import com.app.entity.cars.Car;
import com.app.entity.cars.CarImage;
import com.app.payload.ImageDto;
import com.app.repository.CarImageRepository;
import com.app.repository.CarRepository;
import com.app.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    // Handle image uploading and retrieval
    // Implement endpoints for uploading, retrieving, and deleting images
    // Use Spring MVC or Spring Boot's file upload and download capabilities

    private BucketService bucketService;
    private CarRepository carRepository;
    private CarImageRepository carImageRepository;

    public ImageController(BucketService bucketService, CarRepository carRepository, CarImageRepository carImageRepository) {   //dependency injection
        this.bucketService = bucketService;
        this.carRepository = carRepository;
        this.carImageRepository = carImageRepository;
    }


    //http://localhost:8080/api/v1/images/upload/file/{bucketName}/car/{carId}
    @PostMapping(path = "/upload/file/{bucketName}/car/{carId}")
    public ResponseEntity<CarImage> uploadCarPhotos(
            @RequestParam MultipartFile file,
            @PathVariable String bucketName,
            @PathVariable long carId

            ){

        //take that uploaded file url from BucketService and stored file url in variable String url
        String url = bucketService.uploadFile(file,bucketName);
        Car car = carRepository.findById(carId).get();
        CarImage image = new CarImage();
        image.setUrl(url);
        image.setCar(car);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }
}
