package com.soumyajit.LinkedInMicroservice.uploader_service.Controller;

import com.soumyajit.LinkedInMicroservice.uploader_service.Advices.ApiResponse;
import com.soumyajit.LinkedInMicroservice.uploader_service.Service.CloudeneryUploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class Controller {

    private final CloudeneryUploaderService cloudeneryUploaderService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> upload(@RequestParam
                                             MultipartFile multipartFile){
        String url = cloudeneryUploaderService.uploader(multipartFile);
        ApiResponse apiResponse = new ApiResponse(url);
        return ResponseEntity.ok(apiResponse);
    }
}
