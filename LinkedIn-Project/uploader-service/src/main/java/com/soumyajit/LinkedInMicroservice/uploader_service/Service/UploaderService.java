package com.soumyajit.LinkedInMicroservice.uploader_service.Service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public interface UploaderService {
     public String uploader(MultipartFile multipartFile);
}
