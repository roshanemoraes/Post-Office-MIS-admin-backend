package com.sep.backend_noAuth.controller.PostmasterController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/postmaster/employee")
public class EmployeeController {
    @GetMapping("live-map")
    public List<Map<String, Object>> getEmployees() {
        List<Map<String, Object>> employees = Arrays.asList(
                Map.of(
                        "name", "Ruwan",
                        "deliveredCount", 3,
                        "pendingCount", 5,
                        "lat", 7.215839,
                        "lng", 79.87138
                ),
                Map.of(
                        "name", "Sujith",
                        "deliveredCount", 2,
                        "pendingCount", 7,
                        "lat", 7.194806,
                        "lng", 79.864943
                ),
                Map.of(
                        "name", "Kumara",
                        "deliveredCount", 8,
                        "pendingCount", 1,
                        "lat", 7.198468,
                        "lng", 79.840138
                ),
                Map.of(
                        "name", "Kalpa",
                        "deliveredCount", 5,
                        "pendingCount", 5,
                        "lat", 7.224183,
                        "lng", 79.855931
                )
        );
        return employees;
    }
}
