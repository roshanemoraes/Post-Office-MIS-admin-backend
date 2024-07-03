package com.sep.backend_noAuth.service;

import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TspController {

    static {
        Loader.loadNativeLibraries();
    }

    static class DataModel {

        public final long[][] distanceMatrix = {
                {0, 7, 1000, 1, 1},
                {7, 0, 3, 1000, 8},
                {1000, 3, 0, 6, 2},
                {1, 1000, 6, 0, 7},
                {1, 8, 2, 7, 0},
        };
        public final int vehicleNumber = 1;
        public final int depot = 0;
    }

    @GetMapping("/solve-tsp")
    public String solveTsp() throws Exception {
        final DataModel data = new DataModel();

        RoutingIndexManager manager =
                new RoutingIndexManager(data.distanceMatrix.length, data.vehicleNumber, data.depot);

        RoutingModel routing = new RoutingModel(manager);

        final int transitCallbackIndex =
                routing.registerTransitCallback((long fromIndex, long toIndex) -> {
                    int fromNode = manager.indexToNode(fromIndex);
                    int toNode = manager.indexToNode(toIndex);
                    return data.distanceMatrix[fromNode][toNode];
                });

        routing.setArcCostEvaluatorOfAllVehicles(transitCallbackIndex);

        RoutingSearchParameters searchParameters =
                main.defaultRoutingSearchParameters()
                        .toBuilder()
                        .setFirstSolutionStrategy(FirstSolutionStrategy.Value.PATH_CHEAPEST_ARC)
                        .build();

        Assignment solution = routing.solveWithParameters(searchParameters);

        return printSolution(routing, manager, solution);
    }

    static String printSolution(RoutingModel routing, RoutingIndexManager manager, Assignment solution) {
        String result = "Objective: " + solution.objectiveValue() + "miles\n";
        result += "Route:\n";
        long routeDistance = 0;
        String route = "";
        long index = routing.start(0);
        while (!routing.isEnd(index)) {
            route += manager.indexToNode(index) + " -> ";
            long previousIndex = index;
            index = solution.value(routing.nextVar(index));
            routeDistance += routing.getArcCostForVehicle(previousIndex, index, 0);
        }
        route += manager.indexToNode(routing.end(0));
        result += route + "\n";
        result += "Route distance: " + routeDistance + "miles";
        return result;
    }
}