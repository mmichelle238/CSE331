package marvel;

import graph.Graph;
import graph.Graph.graphEdge;

import java.util.*;

/**
 *<b>MarvelPaths</b> has methods that parse through any given file to create a Graph,
 * and then can find any path between any two nodes in the graph. It can interact with
 * the user by asking them what the beginning and the end of the path is.
 */

public class MarvelPaths {

    //A constant that represents the file that the class will be using to find paths
    public static final String fileName = "marvel.tsv";

    //the main method that can take in the source and destination for a path from the
    //scanner and return the path
    public static void main(String[] args) {
        Graph<String,String> marvelGraph = parseData(fileName);
        Scanner console = new Scanner(System.in);
        System.out.println("Look for paths in "+fileName + "!");
        String cont = "yes";
        while(cont.equalsIgnoreCase("yes")){
            System.out.print("Type the beginning of the path: ");
            String src = console.nextLine();
            System.out.print("Type the end of the path: ");
            String dest = console.nextLine();
            if(!marvelGraph.listNodes().contains(src)) {
                System.out.println("unknown character "+src);
            }
            if(!marvelGraph.listNodes().contains(dest)){
                System.out.println("unknown character "+dest);
            }
            try {
                List<graphEdge> path = findPath(src, dest, marvelGraph);
                System.out.println("path from "+src+" to " + dest + ":");
                for(Graph.graphEdge edge: path) {
                    System.out.println(edge.getNode1()+" to "+edge.getNode2()+ " via "+edge.getLabel());
                }
            } catch (NullPointerException e) {
                System.out.println("This path doesn't exist!");
            }
            System.out.println("Want to look for another path?");
            cont = console.nextLine();
        }
    }

    /**
      * @param fileName is the name of the file that will be parsed to create the Graph
      * @spec.requires that a file exists with the name of the file passed through
      * @return a Graph that holds all of the information from the file
      */

    public static Graph<String,String> parseData(String fileName) {
        Graph<String,String> marvelGraph = new Graph<>();
        Map<String, List<String>> marvelMap = MarvelParser.parseData(fileName);
        for(String key: marvelMap.keySet()) {
            int size = marvelMap.get(key).size();
            List<String> list = marvelMap.get(key);
            for (int i = 0; i < size; i++) {
                String node1 = list.get(i);
                if (!marvelGraph.listNodes().contains(node1)) {
                    marvelGraph.addNode(node1);
                }
                for (int j = 0; j < size; j++) {
                    if (i != j) {
                        String node2 = list.get(j);
                        marvelGraph.addEdge(node1, node2, key);
                    }
                }
            }
        }
        return marvelGraph;
    }

    /**
     * @param src is the beginning of the path
     * @param dest is the destination of the path
     * @param marvelGraph is the Graph that will be used to find the path between the two given
     *                    nodes
     * @spec.requires the beginning and the destination of the path to exist on the Graph
     * @return is a list of strings that holds the path , returns null if the path isn't found
     */
    public static List<graphEdge> findPath(String src, String dest, Graph<String,String> marvelGraph) {
        Queue<String> nodesToVisit = new LinkedList<>();
        Map<String, List<Graph.graphEdge>> pathForEachNode = new HashMap<>();
        nodesToVisit.add(src);
        pathForEachNode.put(src, new ArrayList<>());
        while (!nodesToVisit.isEmpty()) {
            String currNode = nodesToVisit.remove();
            //results in the shortest path being returned, otherwise it will continue creating paths
            if (currNode.equals(dest)) {
                return pathForEachNode.get(currNode);
            }
            List<Graph<String,String>.graphEdge> children = marvelGraph.listChildren(currNode);
            Collections.sort(children, new EdgeComparator());
            //creates a path connecting to each of the children
            for (Graph<String,String>.graphEdge edge : children) {
                String label = edge.getLabel();
                String node = edge.getNode2();
                if (!pathForEachNode.containsKey(node)) {
                    List<graphEdge> path = new ArrayList<>();
                    path.addAll(pathForEachNode.get(currNode));
                    path.add(edge);
                    pathForEachNode.put(node, path);
                    nodesToVisit.add(node);
                }
            }
        }
        return null;
    }

    private static class EdgeComparator implements Comparator<Graph<String,String>.graphEdge> {
        @Override
        public int compare(Graph<String,String>.graphEdge o1, Graph<String,String>.graphEdge o2) {
            String o1Node1 = o1.getNode2();
            String o2Node1 = o2.getNode2();
            if (o1Node1.equals(o2Node1)) {
                return o1.getLabel().compareTo(o2.getLabel());
            } else {
                return o1Node1.compareTo(o2Node1);
            }
        }
    }

}