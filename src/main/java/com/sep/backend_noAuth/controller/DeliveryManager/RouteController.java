package com.sep.backend_noAuth.controller.DeliveryManager;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/delivery-manager/route")
public class RouteController {

    @GetMapping("list-all")
    public List<Map<String, Object>> getRouteDetail() {
        List<Map<String, Object>> routes = Arrays.asList(
                Map.of(
                        "id", "62",
                        "postman_id", "Emp003",
                        "date", "2024-06-19T10:00:00Z",
                        "destinations", Arrays.asList(
                                Map.of(
                                        "id", 1,
                                        "lat", 7.210686,
                                        "lng", 79.835901
                                ),
                                Map.of(
                                        "id", 2,
                                        "lat", 7.213954,
                                        "lng", 79.847701
                                ),
                                Map.of(
                                        "id", 3,
                                        "lat", 7.213504,
                                        "lng", 79.841589
                                )
                        ),
                        "visit_order", "",
                        "zone", "1",
                        "status", "Not Assigned"
                ),
                Map.of(
                        "id", "63",
                        "postman_id", "Emp005",
                        "date", "2024-06-19T10:00:00Z",
                        "destinations", Arrays.asList(
                                Map.of(
                                        "id", 1,
                                        "lat", 7.207853,
                                        "lng", 79.842670
                                ),
                                Map.of(
                                        "id", 2,
                                        "lat", 7.210844,
                                        "lng", 79.853806
                                ),
                                Map.of(
                                        "id", 3,
                                        "lat", 7.216635,
                                        "lng", 79.847841
                                )
                        ),
                        "visit_order", "",
                        "zone", "2",
                        "status", "Not Assigned"
                )
        );
        return routes;
    }
}
