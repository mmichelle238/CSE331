package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * <b>PathFinder</b> has a method to find the path that finds a minimum-cost path between
 * two given nodes in a graph with all non-negative edge weights.
 */

public class PathFinder {

    /**
     * @param start is the beginning of the path
     * @param dest is the destination of the path
     * @param graph1 is the Graph that will be used to find a path between the two given nodes
     * @spec.requires the beginning and end of the path to exist and the graph to exist
     * @return a Path that holds the minimum-cost path between the given nodes, or returns null
     * if the path doesn't exist
     */
    public static <T,V> Path<T> findMinCostPath(T start, T dest, Graph<T,Double> graph1) {
        PriorityQueue<Path<T>> activeNodes = new PriorityQueue<>(new PathComparator<T>());
        activeNodes.add(new Path<>(start));
        Set<T> finishedNodes = new HashSet<>();
        while(!activeNodes.isEmpty()) {
            Path<T> minPath = activeNodes.remove();
            T minDest = minPath.getEnd();
            if(minDest.equals(dest)) {
                return minPath;
            }
            //goes through each path possibility that stems from the last part of the path
            if(!finishedNodes.contains(minDest)) {
                List<Graph<T,Double>.graphEdge> listChildren = graph1.listChildren(minDest);
                for(Graph<T,Double>.graphEdge graphEdge: listChildren) {
                    T node = graphEdge.getNode2();
                    double finValue = graphEdge.getLabel();
                    if(!finishedNodes.contains(node)){
                        Path<T> newPath = minPath.extend(node,finValue);
                        activeNodes.add(newPath);
                    }
                }
                finishedNodes.add(minDest);
            }
        }
        return null;
    }

    private static class PathComparator<T> implements Comparator<Path<T>> {
        @Override
        public int compare(Path<T> o1, Path<T> o2) {
            double o1Cost = o1.getCost();
            double o2Cost = o2.getCost();
            if(o1Cost == o2Cost) {
                return 0;
            } else if(o1Cost > o2Cost) {
                return 1;
            }
            return -1;
        }
    }
}
