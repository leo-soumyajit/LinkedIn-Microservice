package com.soumyajit.LinkedInMicroservice.postService.Client;

import org.springframework.core.io.Resource;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
        name = "uploader-service",
        path = "/uploads/file"
)
public interface UploaderServiceClient {

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("multipartFile") MultipartFile multipartFile);
}

