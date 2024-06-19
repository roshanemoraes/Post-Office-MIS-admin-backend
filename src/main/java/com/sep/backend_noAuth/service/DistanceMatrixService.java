package com.sep.backend_noAuth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DistanceMatrixService {
    private static final String DISTANCE_MATRIX_API = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins={origin}&destinations={destination}&key={apiKey}";

    @Value("${google.maps.api.key}")
    private String apiKey;

    public ResponseEntity<String> getDistanceMatrix(Map<String,Double> origin, List<Map<String,Double>> destinations) {
        String originString = origin.get("lat") + "," + origin.get("lng");
        String destinationString = destinations.stream()
                .map(destination -> destination.get("lat") + "," + destination.get("lng"))
                .collect(Collectors.joining("|"));
        System.out.println(destinationString);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(DISTANCE_MATRIX_API, String.class, originString, destinationString, apiKey);
        return response;
    }
}
