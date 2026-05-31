package com.app.controller;

import com.app.service.GeolocationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {

    private GeolocationService geolocationService;

    public LocationController(GeolocationService geolocationService) {
        this.geolocationService = geolocationService;
    }

    @GetMapping("/get-location")
    public String getLocation(HttpServletRequest request){
        //based on the request it'll call to ClientIp
        String clientIp = getClientIp(request);
        String locationInfo = geolocationService.getLocation("192.140.152.144");
        return locationInfo;
    }

    //ClientIp will take that url details and based on the url is going to get me remoteAddress
    private String getClientIp(HttpServletRequest request){
        String remoteAddr =request.getRemoteAddr();
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if(xForwardedFor != null && !xForwardedFor.isEmpty()) {
            remoteAddr = xForwardedFor.split(",")[0];
        }
        return remoteAddr;

    }
}
