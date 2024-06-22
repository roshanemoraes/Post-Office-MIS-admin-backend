package com.sep.backend_noAuth.controller.DeliveryManagerController;

import com.sep.backend_noAuth.dto.DeliveryManagerDTO.AddressResponse;
import com.sep.backend_noAuth.dto.OptRoutePOST;
import com.sep.backend_noAuth.entity.User;
import com.sep.backend_noAuth.service.DistanceMatrixService;
import com.sep.backend_noAuth.service.TspService;
import com.sep.backend_noAuth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/matrix")
public class DistanceMatrixController {

    @Autowired
    private DistanceMatrixService distanceMatrixService;

    @Autowired
    private TspService tspService;
    @Autowired
    private UserService userService;



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

    @PostMapping("/post-matrix")
    public String getDistanceMatrix(@RequestBody OptRoutePOST optRoutePOST) throws Exception {
        int[][] matrix = optRoutePOST.getMatrix();
        List<String> addresses = optRoutePOST.getAddresses();
        int vehicleNumber = 1;
        int startLocationIndex = 0;

        TspService.DataModel dataModel = new TspService.DataModel(matrix,vehicleNumber,startLocationIndex);
        return tspService.solveTsp(dataModel);
    }

    @GetMapping("/list-postman")
    public List<User> getListOfPostman(){
        return userService.getListOfPostman();
    }

//    public AddressResponse getAddressResponse(@RequestBody List<String> addressIdList){
//
//    }

}
