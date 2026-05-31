package com.app.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeolocationService {

    //to get location we required api key and url
    private static final String API_KEY ="783666928b4a6b33356efae76c47f244";
    private static final String URL = "http://api.ipstack.com/";

    public String getLocation(String ipAddress){
        RestTemplate restTemplate = new RestTemplate();
        String url = URL + ipAddress + "?access_key=" + API_KEY;
        ResponseEntity<String> response = restTemplate.getForEntity(url,String.class);
        return response.getBody();  //can process the response as needed(JSON,)
    }
}
