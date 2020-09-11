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

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

/**
 *<b>CampusMap</b> is an immutable class which implements ModelAPI which helps create a map of the Campus
 * which can be then used to find the shortest distance between buildings.
 */

public class CampusMap implements ModelAPI {

    //Abstraction Function:
    //for a given pair of files, a Graph can be created and used to find the
    //shortest path (based on distance between the Points) based on the Graph
    //created by the given files.
    //
    //Representation Invariant:
    //all of the file names have to exist

    //holds the name of all the files to be used
    private String buildingFile = "";
    private String pathFile = "";
    //holds the campus graph
    private Graph<Point,Double> campusGraph = null;

    /**
     * @param buildingFile is the file name containing all of the building information
     * @param pathFile is the file name containing all of the path information
     */
    public CampusMap(String buildingFile, String pathFile) {
        this.buildingFile = buildingFile;
        this.pathFile = pathFile;
    }

    //creates the graph for the CampusMap object
    private void loadGraph() {
        List<CampusPath> pathList = CampusPathsParser.parseCampusPaths(pathFile);
        campusGraph = new Graph<>();
        for (CampusPath cp : pathList) {
            Point src = new Point(cp.getX1(), cp.getY1());
            Point dest = new Point(cp.getX2(), cp.getY2());
            if (!campusGraph.listNodes().contains(src)) {
                campusGraph.addNode(src);
            }
            if (!campusGraph.listNodes().contains(dest)) {
                campusGraph.addNode(dest);
            }
            campusGraph.addEdge(src, dest, cp.getDistance());
            campusGraph.addEdge(dest, src, cp.getDistance());
        }
    }

    /**
     * @param shortName The short name of a building to query.
     * @return {@literal true} iff the short name provided exists in this campus map.
     */
    @Override
    public boolean shortNameExists(String shortName) {
        Map<String,String> map = buildingNames();
        return map.containsKey(shortName);
    }

    /**
     * @param shortName The short name of a building to look up.
     * @return The long name of the building corresponding to the provided short name.
     * @throws IllegalArgumentException if the short name provided does not exist.
     */
    @Override
    public String longNameForShort(String shortName) {
        if(!shortNameExists(shortName)) {
            throw new IllegalArgumentException("shortName given doesn't exist");
        }
        Map<String,String> map = buildingNames();
        return map.get(shortName);
    }

    /**
     * @return A mapping from all the buildings' short names to their long names in this campus map.
     */
    @Override
    public Map<String, String> buildingNames() {
        List<CampusBuilding> buildingList = CampusPathsParser.parseCampusBuildings(buildingFile);
        Map<String,String> buildingGraph = new HashMap<>();
        for(CampusBuilding cb: buildingList){
            buildingGraph.put(cb.getShortName(),cb.getLongName());
        }
        return buildingGraph;
    }

    /**
     * Finds the shortest path, by distance, between the two provided buildings.
     *
     * @param startShortName The short name of the building at the beginning of this path.
     * @param endShortName   The short name of the building at the end of this path.
     * @return A path between {@code startBuilding} and {@code endBuilding}, or {@literal null}
     * if none exists.
     * @throws IllegalArgumentException if {@code startBuilding} or {@code endBuilding} are
     *                                  {@literal null}, or not valid short names of buildings in
     *                                  this campus map.
     */
    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if(campusGraph == null) {
            loadGraph();
        }
        List<CampusBuilding> buildingList = CampusPathsParser.parseCampusBuildings(buildingFile);
        Point src = null;
        Point dest = null;
        for (CampusBuilding cb : buildingList) {
            if (cb.getShortName().equals(startShortName)) {
                src = new Point(cb.getX(), cb.getY());
            }
            if (cb.getShortName().equals(endShortName)) {
                dest = new Point(cb.getX(), cb.getY());
            }
        }
        return PathFinder.findMinCostPath(src,dest,campusGraph);
    }

}
