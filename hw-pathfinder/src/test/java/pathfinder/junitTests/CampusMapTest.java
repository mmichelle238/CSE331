package pathfinder.junitTests;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;

public class CampusMapTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    @Test
    public void testMinPath(){
        CampusMap campusMap= new CampusMap("test_buildings.tsv","test_paths.tsv");
        Path<Point> finalPath = campusMap.findShortestPath("NB","EP");
        Path<Point> perfectPath = new Path<>(new Point(1.00,3.00));
        perfectPath = perfectPath.extend(new Point(4.00,7.00),20.00);
        perfectPath = perfectPath.extend(new Point(12.00,10.00),1.00);
        perfectPath = perfectPath.extend(new Point(7.00,9.00),2.00);
        perfectPath = perfectPath.extend(new Point(1.0,1.0),10.0);
        assert(perfectPath.getCost() == finalPath.getCost());
    }

    @Test
    public void testMinPath2(){
        CampusMap campusMap = new CampusMap("test_buildings.tsv","test_paths.tsv");
        Path<Point> finalPath = campusMap.findShortestPath("NB","CA");
        Path<Point> perfectPath = new Path<>(new Point(1.00,3.00));
        perfectPath = perfectPath.extend(new Point(4.00,7.00),20.00);
        perfectPath = perfectPath.extend(new Point(12.00,10.00),1.00);
        perfectPath = perfectPath.extend(new Point(7.00,9.00),2.00);
        assert(perfectPath.getCost() == finalPath.getCost());
    }

    @Test
    public void testNullPath() {
        CampusMap campusMap = new CampusMap("test_buildings.tsv","test_paths.tsv");
        Path<Point> finalPath = campusMap.findShortestPath("SN","LB");
        assert(finalPath == null);
    }

    @Test
    public void testOneJump() {
        CampusMap campusMap = new CampusMap("test_buildings.tsv","test_paths.tsv");
        Path<Point> finalPath = campusMap.findShortestPath("SN","MD");
        Path<Point> perfectPath = new Path<>(new Point(4.00,7.00));
        perfectPath = perfectPath.extend(new Point(12.00,10.00),1.00);
        assert(finalPath.equals(perfectPath));
    }
}
