package com.sep.backend_noAuth.controller.DeliveryManager;

import com.sep.backend_noAuth.dto.OptRoutePOST;
import com.sep.backend_noAuth.entity.UserInfo;
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
    public ResponseEntity<String> getDistanceMatrix() {
        Map<String, Double> location1 = new HashMap<>();
        location1.put("lat", 40.6655101);
        location1.put("lng", -73.89188969999998);

        Map<String, Double> location2 = new HashMap<>();
        location2.put("lat", 40.6905615);
        location2.put("lng", -73.9976592);

        Map<String, Double> location3 = new HashMap<>();
        location3.put("lat", 40.712776);
        location3.put("lng", -74.005974);

        Map<String, Double> location4 = new HashMap<>();
        location4.put("lat", 40.711073);
        location4.put("lng", -74.007840);

        List<Map<String, Double>> locations = Arrays.asList(location1, location2, location3, location4);

        try {
            int[][] adjacencyMatrix = distanceMatrixService.getSquareAdjacencyMatrix(locations);

            StringBuilder matrixString = new StringBuilder();
            for (int[] row : adjacencyMatrix) {
                for (long distance : row) {
                    matrixString.append(distance).append(" ");
                }
                matrixString.append("\n");
            }
            return ResponseEntity.ok(matrixString.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error generating distance matrix.");
        }
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
    public List<UserInfo> getListOfPostman(){
        return userService.getListOfPostman();
    }

//    public AddressResponse getAddressResponse(@RequestBody List<String> addressIdList){
//
//    }

}
