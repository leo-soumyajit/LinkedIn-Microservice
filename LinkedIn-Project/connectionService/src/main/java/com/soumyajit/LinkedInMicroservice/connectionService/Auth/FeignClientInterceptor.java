package com.soumyajit.LinkedInMicroservice.connectionService.Auth;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Long userId = AuthContextHolder.getCurrentUserId();
        if (userId!=null){
            requestTemplate.header("X-User-Id",userId.toString());
        }
    }
}
