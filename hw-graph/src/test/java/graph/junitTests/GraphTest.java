package graph.junitTests;

import graph.Graph;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import java.util.*;

import static org.junit.Assert.*;

public final class GraphTest {

    @Rule public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    @Test
    public void testAddOneNode() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        assertNotNull(graph1);
        Set<String> listNode = graph1.listNodes();
        Set<String> listNodeTest = new HashSet<>();
        listNodeTest.add("A");
        assertEquals(listNodeTest, listNode);
    }

    @Test
    public void testAddOneEdge() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        graph1.addNode("B");
        graph1.addEdge("A","B","5");
        List<String> children = graph1.listChildren("A", false);
        List<String> edge = new ArrayList<>();
        edge.add("B");
        assertEquals(edge, children);
    }

    @Test
    public void testAddTwoEdges() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        graph1.addNode("B");
        graph1.addEdge("A","B","5");
        graph1.addEdge("A", "B", "3");
        List<String> children = graph1.listChildren("A", false);
        List<String> edge = new ArrayList<>();
        edge.add("B");
        assertEquals(edge,children);
    }

    @Test
    public void testAddTwoEdges2() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        graph1.addNode("B");
        graph1.addNode("C");
        graph1.addEdge("A","B","5");
        graph1.addEdge("A", "C", "3");
        List<String> children = graph1.listChildren("A", false);
        List<String> edge = new ArrayList<>();
        edge.add("B");
        edge.add("C");
        assertEquals(edge, children);
    }

    @Test
    public void testAddTwoEdges3() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        graph1.addNode("B");
        graph1.addNode("C");
        graph1.addEdge("A","B","5");
        graph1.addEdge("B", "C", "3");
        List<String> children = graph1.listChildren("A", false);
        List<String> edge = new ArrayList<>();
        edge.add("B");
        assertEquals(edge, children);
        List<String> children1 = graph1.listChildren("B", false);
        edge.clear();
        edge.add("C");
        assertEquals(edge, children1);
    }

    @Test
    public void testAddEdgeToItself() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        graph1.addNode("B");
        graph1.addEdge("A","A","5");
        List<String> children = graph1.listChildren("A",false);
        List<String> edge = new ArrayList<>();
        edge.add("A");
        assertEquals(edge,children);
    }

    @Test
    public void testRemoveOnlyNode() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        graph1.removeNode("A");
        Set<String> listNode = graph1.listNodes();
        assertTrue(listNode.isEmpty());
    }

    @Test
    public void testRemoveNode() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        graph1.addNode("B");
        assertFalse(graph1.listNodes().isEmpty());
        Set<String> listNodeTest = new HashSet<>();
        listNodeTest.add("A");
        graph1.removeNode("B");
        Set<String> listNode = graph1.listNodes();
        assertEquals(listNodeTest, listNode);
    }

    @Test
    public void testRemoveNodeWithEdges() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        graph1.addNode("B");
        graph1.addNode("C");
        graph1.addEdge("A","B","5");
        graph1.addEdge("C", "B", "3");
        graph1.addEdge("B","C","2");
        graph1.addEdge("B","A","1");
        graph1.addEdge("A","C","4");
        graph1.removeNode("B");
        List<String> childrenA = graph1.listChildren("A", false);
        List<String> childrenC = graph1.listChildren("C", false);
        List<String> childA = new ArrayList<>();
        List<String> childC = new ArrayList<>();
        childA.add("C");
        //Tests that the nodes aren't connected to B
        assertEquals(childA, childrenA);
        assertEquals(childC, childrenC);
        //Tests that the B is removed from the graph
        Set<String> listNode = graph1.listNodes();
        Set<String> listNodeTest = new HashSet<>();
        listNodeTest.add("A");
        listNodeTest.add("C");
        assertEquals(listNodeTest, listNode);
    }

    @Test
    public void testRemoveEdge() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        graph1.addNode("B");
        graph1.addNode("C");
        graph1.addEdge("A","B","5");
        graph1.addEdge("C", "B", "3");
        graph1.addEdge("B","C","2");
        graph1.addEdge("B","A","1");
        graph1.addEdge("A","C","4");
        graph1.removeEdge("A","B","5");
        List<String> childrenA = graph1.listChildren("A", false);
        List<String> childrenB = graph1.listChildren("B",false);
        List<String> childrenC = graph1.listChildren("C",false);
        List<String> childA = new ArrayList<>();
        childA.add("C");
        List<String> childB = new ArrayList<>();
        childB.add("A");
        childB.add("C");
        List<String> childC = new ArrayList<>();
        childC.add("B");
        Collections.sort(childrenB);
        Collections.sort(childrenA);
        Collections.sort(childrenC);
        assertEquals(childA, childrenA);
        assertEquals(childB,childrenB);
        assertEquals(childC, childrenC);
    }

    @Test
    public void testRemoveEdgeFromItself() {
        Graph<String, String> graph1 = new Graph<>();
        graph1.addNode("A");
        graph1.addEdge("A","A","2");
        List<String> childA = new ArrayList<>();
        graph1.removeEdge("A","A","2");
        List<String> childrenA = graph1.listChildren("A",false);
        assertEquals(childA, childrenA);
    }

}
