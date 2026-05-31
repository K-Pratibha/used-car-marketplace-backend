package com.app.controller;

import com.app.entity.cars.Brand;
import com.app.repository.BrandRepository;
import com.app.service.BulkCarUploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/brand/bulk-upload")
public class BrandBulkUploadController {

    private BulkCarUploadService bulkCarUploadService;
    private BrandRepository brandRepository;

    public BrandBulkUploadController(BulkCarUploadService bulkCarUploadService, BrandRepository brandRepository) {
        this.bulkCarUploadService = bulkCarUploadService;
        this.brandRepository = brandRepository;
    }

    @PostMapping("/upload")
    public String uploadExcelFile(){
        try {
            String filePath = "C:\\Users\\Administrator\\OneDrive\\Documents\\Book1.xlsx";
            List<Brand> brands = bulkCarUploadService.readExcel(filePath);
            brandRepository.saveAll(brands);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "uploaded";
    }

}
