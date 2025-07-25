package com.soumyajit.LinkedInMicroservice.uploader_service.Service;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class CloudeneryUploaderService implements UploaderService{

    private final Cloudinary cloudinary;

    @Override
    public String uploader(MultipartFile multipartFile) {
        try {
            Map uploadResult = uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(),
                    Map.of());
            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
