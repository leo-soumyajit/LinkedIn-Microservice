package com.soumyajit.LinkedInMicroservice.uploader_service.Controller;

import com.soumyajit.LinkedInMicroservice.uploader_service.Service.CloudeneryUploaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class Controller {

    private final CloudeneryUploaderService cloudeneryUploaderService;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> upload(@RequestPart("multipartFile")
                                             MultipartFile multipartFile){
        String url = cloudeneryUploaderService.uploader(multipartFile);
        return ResponseEntity.ok(url);
    }
}
