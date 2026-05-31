package com.app.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class BucketService {
    // implement bucket service methods
    // such as uploading, downloading, listing, deleting files etc.
    // use AWS SDK for Java to interact with S3 bucket
    @Autowired
    private AmazonS3 amazonS3;

    public String uploadFile(MultipartFile file, String bucketName){
        if (file.isEmpty()){
            throw new IllegalStateException("cannot upload empty file");
        }
        try {
            // upload file to S3 bucket
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" +  file.getOriginalFilename());
            file.transferTo(convFile);
            try{
                amazonS3.putObject(bucketName,convFile.getName(), convFile);
                //return file url
                        return amazonS3.getUrl(bucketName,file.getOriginalFilename()).toString();
            }catch (AmazonS3Exception S3Exception){
                // handle S3 specific exceptions
                return "Error while uploading file to S3: " + S3Exception.getMessage();
            }

        }catch (Exception e){
            throw new IllegalArgumentException("Failed to upload file");
        }

    }
}
