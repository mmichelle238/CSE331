package graph;

import java.util.*;

/**
 * <b>Graph</b> is a mutable representation of a graph with a collection of nodes
 * and edges to create a directed labeled graph. Edges can only be created after the node
 * is created, and edges with the same nodes can't have the same label as another edge that
 * already exists with the same nodes and label. For example, there can't be two edges from
 * node A to B with the value 5. If the two edges have different values, that is ok.
 */

public class Graph<T,V>{

    private Map<T,List<graphEdge>> graph;

    private boolean checkRep = false;

    //Abstraction Function:
    //for a given node in the graph, there is a list of
    //edges(graph.get(node)) that connect them to other
    //nodes, which creates a directed labeled graph
    //
    //Representation Invariant:
    //graph != null &&
    //graph.get(node).size != 0, the list for the node can be null or greater than 0

    /**
     * @spec.effects creates a graph
     */
    public Graph(){
        graph = new HashMap<>(50);
        checkRep();
    }

    /**
     * @param node item to be added to the graph
     * @spec.requires node to not exist in the graph
     * @spec.effects creates a new node in the graph
     * @spec.modifies this
     */
    public void addNode(T node){
        checkRep();
        graph.put(node, new ArrayList<graphEdge>());
        checkRep();
    }

    /**
     * @param node1 the source of the edge
     * @param node2 the destination of the edge
     * @param label name of the edge that gets created
     * @spec.requires both node1 and node2 to exist in graph and for the edge
     * to not exist with the same nodes and label
     * @spec.modifies this
     */
    public void addEdge(T node1, T node2, V label){
        checkRep();
        List<graphEdge> addEdge = graph.get(node1);
        addEdge.add(new graphEdge(node1,node2,label));
        checkRep();
    }

    /**
     * @param node item to be removed from the graph
     * @spec.requires node to exist in the graph
     * @spec.effects removes node from the graph, including any children and parents
     * of the node
     * @spec.modifies this
     */
    public void removeNode(T node){
        checkRep();
        graph.remove(node);
        for(T key: graph.keySet()) {
            Iterator<graphEdge> iterator = graph.get(key).iterator();
            while(iterator.hasNext()) {
                graphEdge newEdge = iterator.next();
                if(newEdge.node2.equals(node)) {
                    iterator.remove();
                }
            }
        }
        checkRep();
    }

    /**
     * @param node1 the source of the edge
     * @param node2 the destination of the edge
     * @param label name of the edge
     * @spec.requires edge to exist in the graph
     * @spec.effects removes the edge from the graph
     * @spec.modifies this
     */
    public void removeEdge(T node1, T node2, V label){
        checkRep();
        List<graphEdge> listEdge = graph.get(node1);
        Iterator<graphEdge> itr = listEdge.iterator();
        while (itr.hasNext()) {
            graphEdge edge = itr.next();
            if(edge.node2.equals(node2) && edge.label.equals(label)) {
                itr.remove();
            }
        }
        checkRep();
    }

    /**
     * @return a set with all of the nodes in the graph, returns an empty set if no nodes
     */
    public Set<T> listNodes() {
        checkRep();
        return graph.keySet();
    }

    /**
     * @param node item that we want to find the children of
     * @param label true if label should be included, false if only node should be included
     * @return a list of all of the node's children and the label in parentheses, return empty list if no children
     * @spec.requires node to exists
     */
    public List<String> listChildren(T node, boolean label) {
        checkRep();
        List<graphEdge> listEdge = graph.get(node);
        List<String> listChildren = new ArrayList<>();
        for (graphEdge edge : listEdge) {
            if(!listChildren.contains(edge.node2)) {
                if(label) {
                    listChildren.add(edge.node2+"("+edge.label+")");
                } else {
                    listChildren.add(edge.node2+"");
                }
            }
        }
        checkRep();
        return listChildren;
    }

    /**
     * @param node item that we want to find the children of
     * @return a list of all of the node's children, return empty list if no children
     * @spec.requires node to exists
     */
    public List<graphEdge> listChildren(T node) {
        checkRep();
        List<graphEdge> listEdge = graph.get(node);
        List<graphEdge> listChildren = new ArrayList<>();
        for (graphEdge edge : listEdge) {
            if(!listChildren.contains(edge.node2)) {
                listChildren.add(edge);
            }
        }
        checkRep();
        return listChildren;
    }

    private void checkRep() {
        if(checkRep) {
            assert (graph != null) : "graph == null";
            for (T key : graph.keySet()) {
                assert (graph.get(key) != null) : "the list is null";
            }
        }
    }

    public class graphEdge{

        private T node1;
        private T node2;
        private V label;

        //Abstraction Function:
        //for the given nodes and the label, an edge is created
        //joining two nodes together in the Graph
        //
        //Representation Invariant:
        //graph.containsKey(graphEdge.node1) & graph.containsKey(graphEdge.node2) are both true &&
        //edges can't have the same nodes and label

        /**
         * @param node1 connects to node2
         * @param node2 connected to node1
         * @param label name of the edge that gets created
         * @spec.effects creates a new edge
         */
        public graphEdge(T node1, T node2, V label){
            this.node1 = node1;
            this.node2 = node2;
            this.label = label;
            checkRep();
        }

        /**
         * @return the starting node of the edge
         */
        public T getNode1() {
            return node1;
        }

        /**
         * @return the destination node of the edge
         */
        public T getNode2() {
            return node2;
        }

        /**
         * @return the value of the edge
         */
        public V getLabel() {
            return label;
        }

        private void checkRep() {
            if(checkRep) {
                assert (graph.containsKey(node1)) : "node1 doesn't exist in graph";
                assert (graph.containsKey(node2)) : "node2 doesn't exist in graph";
                List<graphEdge> list = graph.get(node1);
                for (graphEdge e : list) {
                    if (e.node1.equals(node1) && e.node2.equals(node2)) {
                        assert (!e.label.equals(label)) : "this edge already exists";
                    }
                }
            }
        }

    }

}
