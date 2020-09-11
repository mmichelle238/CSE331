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

package pathfinder.scriptTestRunner;

import graph.Graph;
import marvel.MarvelPaths;
import pathfinder.PathFinder;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

import static marvel.MarvelPaths.parseData;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    public static void main(String[] args) {
        // You only need a main() method if you choose to implement
        // the 'interactive' test driver, as seen with GraphTestDriver's sample
        // code. You may also delete this method entirely and just
    }
    private final HashMap<String, Graph<String,Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch (command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        graphs.put(graphName,new Graph<String, Double>());
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        Graph<String,Double> graph1 = graphs.get(graphName);
        graph1.addNode(nodeName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        Graph<String,Double> graph1 = graphs.get(graphName);
        double edge = Double.parseDouble(edgeLabel);
        graph1.addEdge(parentName, childName, edge);
        output.println(String.format("added edge %.3f from ", edge)+parentName+" to "+childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        Graph<String,Double> graph1 = graphs.get(graphName);
        Set<String> listNodes = graph1.listNodes();
        Set<String> sortedNodes = new TreeSet<>();
        sortedNodes.addAll(listNodes);
        output.print( graphName+" contains:" );
        for(String str: sortedNodes) {
            output.print(" "+str);
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        Graph<String,Double> graph1 = graphs.get(graphName);
        List<String> listChildren = graph1.listChildren(parentName, true);
        Collections.sort(listChildren);
        output.print("the children of "+parentName+" in "+graphName+" are:" );
        for(String str: listChildren) {
            output.println(" "+str);
        }
        output.println();
    }

    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }
        String graphName = arguments.get(0);
        String srcName = arguments.get(1);
        String destName = arguments.get(2);

        findPath(graphName, srcName, destName);
    }

    private void findPath(String graphName, String src, String dest) {
        Graph<String,Double> newGraph = graphs.get(graphName);
        try{
            if(!newGraph.listNodes().contains(src) || !newGraph.listNodes().contains(dest)) {
                if(!newGraph.listNodes().contains(src)) {
                    output.println("unknown node "+src);
                }
                if(!newGraph.listNodes().contains(dest)){
                    output.println("unknown node "+dest);
                }
            } else{
                Path<String> path = PathFinder.findMinCostPath(src,dest,newGraph);
                output.println("path from "+src+" to " + dest + ":");
                double totalCost = 0.000;
                Iterator<Path<String>.Segment> itr = path.iterator();
                while(itr.hasNext()){
                    Path<String>.Segment nextSeg = itr.next();
                    String start = nextSeg.getStart();
                    String end = nextSeg.getEnd();
                    double segCost = nextSeg.getCost();
                    output.println(start+" to "+end + String.format(" with weight %.3f",segCost));
                    totalCost += segCost;
                }
                output.println(String.format("total cost: %.3f",totalCost));
            }
        }catch(NullPointerException e){
            output.println("no path found");
        }
    }

    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }

}
