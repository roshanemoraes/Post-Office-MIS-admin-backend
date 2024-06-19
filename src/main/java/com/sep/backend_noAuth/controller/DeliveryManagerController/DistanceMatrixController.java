package com.sep.backend_noAuth.controller.DeliveryManagerController;

import com.sep.backend_noAuth.service.DistanceMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matrix")
public class DistanceMatrixController {

    @Autowired
    private DistanceMatrixService distanceMatrixService;

    public DistanceMatrixController(DistanceMatrixService distanceMatrixService) {
        this.distanceMatrixService = distanceMatrixService;
    }

    @GetMapping("/distance")
    public ResponseEntity<String> getDistanceMatrix(){
        Map<String, Double> origin = new HashMap<>();
        origin.put("lat", 40.6655101);
        origin.put("lng", -73.89188969999998);

        Map<String, Double> destination1 = new HashMap<>();
        destination1.put("lat", 40.6905615);
        destination1.put("lng", -73.9976592);

        Map<String, Double> destination2 = new HashMap<>();
        destination2.put("lat", 40.712776);
        destination2.put("lng", -74.005974);

        List<Map<String,Double>> destinations = Arrays.asList(destination1,destination2);
        return distanceMatrixService.getDistanceMatrix(origin,destinations);
    }
}
