package com.app.controller;

import com.app.entity.cars.Car;
import com.app.entity.cars.CarImage;
import com.app.service.BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/actual-car-photos"  )
public class ActualCarPhotos {

    private BucketService bucketService;

    public ActualCarPhotos(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    //http://localhost:8080/api/v1/images/upload/file/{bucketName}/car/{carId}
    @PostMapping(path = "/upload/file/{bucketName}/car/{carId}")
    public String uploadCarPhotos(
            @RequestParam List<MultipartFile> files,
            @PathVariable String bucketName,
            @PathVariable long carId

    ) {

        ArrayList<String> carImages = new ArrayList<>();
        //it will copy this files from file one by one and save in the bucket list

        for (MultipartFile file : files) {
            String url = bucketService.uploadFile(file, bucketName);
            //carImages.add(url);
            //save this data in the CarEvaluationPhotos table in the database with car id number
        }
        return null;  //return the list of car images to the client after successful upload
    }
}
