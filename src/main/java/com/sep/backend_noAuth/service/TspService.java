package com.sep.backend_noAuth.service;

import com.google.ortools.Loader;
import com.google.ortools.constraintsolver.*;
import org.springframework.stereotype.Service;

@Service
public class TspService {

    static {
        Loader.loadNativeLibraries();
    }

    public record DataModel(int[][] distanceMatrix, int vehicleNumber, int depot) {
    }

    public String solveTsp(DataModel data) throws Exception {
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
        String result="";
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
        result += route;
        return result;
    }
}

//Original
    /*
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
     */