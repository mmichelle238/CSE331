/*
 * Copyright (C) 2020 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2020 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Spark;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SparkServer {

    //creates an instance of the CampusMap object which will be used for the Spark paths
    public static final CampusMap campusMap = new CampusMap("campus_buildings.tsv","campus_paths.tsv");

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        //returns a list of all of the building names
        Spark.get("/buildingNames",(req, res) -> {
            res.type("text/plain");
            Map<String, String> buildings = campusMap.buildingNames();
            List<String> buildingsList = new ArrayList<>();
            for(String shortName: buildings.keySet()){
                String buildingName = buildings.get(shortName)+" {"+shortName+"}";
                buildingsList.add(buildingName);
            }
            Gson gson = new Gson();
            return gson.toJson(buildingsList);
        });

        //returns a list of all of the points for the path based on the given the source and destination
        Spark.get("/findPath",(req, res) -> {
            String start = req.queryParams("src");
            String end = req.queryParams("dest");
            res.type("text/plain");
            Path<Point> shortestPath = campusMap.findShortestPath(start,end);
            Iterator<Path<Point>.Segment> itr = shortestPath.iterator();
            List<Point> finalPath = new ArrayList<>();
            while(itr.hasNext()) {
                Path<Point>.Segment firstSeg = itr.next();
                Point begin = firstSeg.getStart();
                Point ending = firstSeg.getEnd();
                finalPath.add(begin);
                finalPath.add(ending);
            }
            Gson gson = new Gson();
            return gson.toJson(finalPath);
        });

    }

}
