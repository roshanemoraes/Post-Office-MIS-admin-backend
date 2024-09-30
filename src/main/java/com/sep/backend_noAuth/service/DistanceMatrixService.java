package com.sep.backend_noAuth.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sep.backend_noAuth.dto.DestinationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DistanceMatrixService {
    private static final String DISTANCE_MATRIX_API = "https://maps.googleapis.com/maps/api/distancematrix/json?units=imperial&origins={origins}&destinations={destinations}&key={apiKey}";

    @Value("${google.maps.api.key}")
    private String apiKey;

    @Autowired
    private TspService tspService;

    public String getOptimizedRoute(List<DestinationDto> destinations) throws Exception {
        List<Map<String, Double>> locations = new ArrayList<>();
        for (DestinationDto destination:destinations){
            Map<String, Double> location = new HashMap<>();
            location.put("lat",destination.getLat());
            location.put("lng",destination.getLng());
            locations.add(location);
        }
        int[][] adjacencyMatrix = getSquareAdjacencyMatrix(locations);

        int vehicleNumber = 1;
        int startLocationIndex = 0;
        TspService.DataModel dataModel = new TspService.DataModel(adjacencyMatrix,vehicleNumber,startLocationIndex);
        return tspService.solveTsp(dataModel);

    }

    public ResponseEntity<String> getDistanceMatrix(List<Map<String, Double>> locations) {
        String locationsString = locations.stream()
                .map(location -> location.get("lat") + "," + location.get("lng"))
                .collect(Collectors.joining("|"));

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(DISTANCE_MATRIX_API, String.class, locationsString, locationsString, apiKey);
    }

    public int[][] getSquareAdjacencyMatrix(List<Map<String, Double>> locations) throws Exception {
        // Get the distance matrix from the API
        ResponseEntity<String> responseEntity = getDistanceMatrix(locations);
        String jsonResponse = responseEntity.getBody();

        // Parse the JSON response
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonResponse);

        // Extract the rows containing the distance data
        JsonNode rows = rootNode.get("rows");

        int n = locations.size();

        // Initialize the square adjacency matrix (n x n)
        int[][] adjacencyMatrix = new int[n][n];

        // Populate the adjacency matrix
        for (int i = 0; i < n; i++) {
            JsonNode elements = rows.get(i).get("elements");
            for (int j = 0; j < n; j++) {
                JsonNode element = elements.get(j);
                if ("OK".equals(element.get("status").asText())) {
                    adjacencyMatrix[i][j] = Math.toIntExact(element.get("distance").get("value").asLong()); // Distance in meters
                } else if (i == j) {
                    adjacencyMatrix[i][j] = 0; // Distance to self is 0
                } else {
                    adjacencyMatrix[i][j] = Integer.MAX_VALUE; // Unreachable destinations get a large value
                }
            }
        }

        // Make the matrix symmetric
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                // If both directions are OK, take the average
                if (adjacencyMatrix[i][j] != Integer.MAX_VALUE && adjacencyMatrix[j][i] != Integer.MAX_VALUE) {
                    int averageDistance = (adjacencyMatrix[i][j] + adjacencyMatrix[j][i]) / 2;
                    adjacencyMatrix[i][j] = averageDistance;
                    adjacencyMatrix[j][i] = averageDistance;
                } else if (adjacencyMatrix[i][j] == Integer.MAX_VALUE && adjacencyMatrix[j][i] != Integer.MAX_VALUE) {
                    adjacencyMatrix[i][j] = adjacencyMatrix[j][i]; // Assign reverse distance
                } else if (adjacencyMatrix[i][j] != Integer.MAX_VALUE && adjacencyMatrix[j][i] == Integer.MAX_VALUE) {
                    adjacencyMatrix[j][i] = adjacencyMatrix[i][j]; // Assign reverse distance
                }
            }
        }

        return adjacencyMatrix;
    }


}
