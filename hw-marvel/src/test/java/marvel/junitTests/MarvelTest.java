package marvel.junitTests;

import graph.Graph;
import marvel.MarvelPaths;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class MarvelTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    @Test
    public void testCompareTwoGraphs() {
        Graph<String,String> marvel = MarvelPaths.parseData("staffSuperheroes.tsv");
        Set<String> marvelSet = marvel.listNodes();
        Graph<String,String> marvelOG = new Graph<>();
        marvelOG.addNode("Ernst-the-Bicycling-Wizard");
        marvelOG.addNode("Notkin-of-the-Superhuman-Beard");
        marvelOG.addNode("Perkins-the-Magical-Singing-Instructor");
        marvelOG.addNode("Grossman-the-Youngest-of-them-all");
        marvelOG.addEdge("Ernst-the-Bicycling-Wizard","Notkin-of-the-Superhuman-Beard","CSE331");
        marvelOG.addEdge("Ernst-the-Bicycling-Wizard","Notkin-of-the-Superhuman-Beard","CSE403");
        marvelOG.addEdge("Ernst-the-Bicycling-Wizard","Perkins-the-Magical-Singing-Instructor","CSE331");
        marvelOG.addEdge("Ernst-the-Bicycling-Wizard","Grossman-the-Youngest-of-them-all","CSE331");
        Set<String> marvelOGSet = marvelOG.listNodes();
        assert(marvelSet.equals(marvelOGSet));
        List<String> marvelChildA = marvel.listChildren("Ernst-the-Bicycling-Wizard",false);
        List<String> marvelOGChildA = marvelOG.listChildren("Ernst-the-Bicycling-Wizard",false);
        assert(marvelChildA.equals(marvelOGChildA));

    }

}
