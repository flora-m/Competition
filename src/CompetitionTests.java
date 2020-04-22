/*
 *  @author Barbara Flora Molnar
 */
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import org.junit.Test;


public class CompetitionTests {

//  testing on my computer
	String "tinyEWD.txt" = "C:\\Uni\\CS\\java\\Competition\\data\\tinyEWD.txt";
	String "tinyTest.txt" = "C:\\Uni\\CS\\java\\Competition\\data\\tinyTest.txt";
	String "empty.txt" = "C:\\Uni\\CS\\java\\Competition\\data\\empty.txt";


	// Dijkstra tests

	@Test
	public void testDijkstraConstructor() {

		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyEWD.txt", 60, 70, 80);
		assertEquals("Testing constructor with valid data file", 80, dijkstra.sC);
	}

	@Test
	public void testGetFrom() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyEWD.txt", 60, 70, 80);
		dijkstra.createMap();
		CompetitionDijkstra.Node node = dijkstra.map.get(0);
		assertEquals("Testing getFrom() method", 0, node.getFrom());
	}

	@Test
	public void testGetStreets() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyTest.txt", 60, 70, 80);
		dijkstra.createMap();
		CompetitionDijkstra.Node node = dijkstra.map.get(2);
		CompetitionDijkstra.Street street = dijkstra.new Street(3, 260);
		ArrayList<CompetitionDijkstra.Street> streets = new ArrayList<CompetitionDijkstra.Street>();
		streets.add(street);
		assertEquals("Testing getStreets() method", streets.size(), node.getStreets().size());
		assertEquals("Testing getStreets() method", streets.get(0).getTo(), node.getStreets().get(0).getTo());	
	}

	@Test
	public void testGetDistFromSource() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyTest.txt", 60, 70, 80);
		dijkstra.createMap();
		dijkstra.reset();
		dijkstra.calcDistFromSource(0);
		assertEquals("Testing getDistFromSource() method", 660, (int) dijkstra.map.get(3).getDistFromSource() );
	}

	@Test
	public void testGetTo() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyTest.txt", 60, 70, 80);
		dijkstra.createMap();
		CompetitionDijkstra.Node node = dijkstra.map.get(2);
		CompetitionDijkstra.Street street = dijkstra.new Street(3, 260);
		assertEquals("Testing getTo() method", 3, street.getTo());
	}

	@Test
	public void testGetDist() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyTest.txt", 60, 70, 80);
		dijkstra.createMap();
		CompetitionDijkstra.Node node = dijkstra.map.get(2);
		CompetitionDijkstra.Street street = dijkstra.new Street(3, 260);
		ArrayList<CompetitionDijkstra.Street> streets = new ArrayList<CompetitionDijkstra.Street>();
		streets.add(street);
		assertEquals("Testing getDist() method", streets.get(0).getDist(), node.getStreets().get(0).getDist(), 0.00000001);
	}

	@Test 
	public void testDijkstraInvalidFile() {

		CompetitionDijkstra dijkstra = new CompetitionDijkstra("invalidText.txt", 60, 70, 80);
		int temp = dijkstra.timeRequiredforCompetition();
		assertEquals("Testing isValidFile with invalid data file", -1, temp);		
	}

	@Test
	public void testDijkstraInvalidSpeed1() {

		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyEWD.txt", 30, 70, 80);
		assertEquals("Testing isValidSpeed with invalid walking speed", -1, dijkstra.createMap());
	}

	@Test
	public void testDijkstraInvalidSpeed2() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyEWD.txt", 110, 70, 80);
		assertEquals("Testing isValidSpeed with invalid walking speed", -1, dijkstra.createMap());		
	}

	@Test
	public void testEmptyFile() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("empty.txt", 60, 70, 80);
		assertEquals("Testing with an empty file input", -1, dijkstra.timeRequiredforCompetition());
	}

	@Test
	public void testMaxDist() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyTest.txt", 60, 70, 80);
		dijkstra.createMap();
		dijkstra.reset();
		dijkstra.calcDistFromSource(0);
		assertEquals("Testing maxDist()", 660, (int) dijkstra.maxDist());
	}

	@Test
	public void testTimeRequiredForTesting() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyEWD.txt", 60, 70, 80);
		assertEquals("Testing timeRequiredForTesting()", 31, dijkstra.timeRequiredforCompetition());
	}



	// Floyd-Warshall tests

	@Test
	public void testFWConstructor() {
		CompetitionFloydWarshall fw = new CompetitionFloydWarshall("tinyEWD.txt", 60, 70, 80);
		assertEquals("Testing FW constructor with valid inputs", 80, fw.sC);
	}

	@Test 
	public void testFWInvalidFile() {
		CompetitionFloydWarshall fw = new CompetitionFloydWarshall("invalidText.txt", 60, 70, 80);
		assertEquals("Testing FW constructor with invalid file input", -1, fw.timeRequiredforCompetition());
	}

	@Test
	public void testFWEmptyFile() {
		CompetitionFloydWarshall fw = new CompetitionFloydWarshall("empty.txt", 60, 70, 80);
		assertEquals("Testing FW constructor with empty file input", -1, fw.timeRequiredforCompetition());
	}

	@Test
	public void testFWInvalidSpeed1() {
		CompetitionFloydWarshall fw = new CompetitionFloydWarshall("tinyEWD.txt", 30, 70, 80);
		assertEquals("Testing FW constructor with invalid speed input", -1, fw.timeRequiredforCompetition());
	}

	@Test
	public void testFWInvalidSpeed2() {
		CompetitionFloydWarshall fw = new CompetitionFloydWarshall("tinyEWD.txt", 60, 70, 1000);
		assertEquals("Testing FW constructor with invalid speed input", -1, fw.timeRequiredforCompetition());
	}

	@Test 
	public void testFWMaxDist() {
		CompetitionFloydWarshall fw = new CompetitionFloydWarshall("tinyEWD.txt", 60, 70, 80);
		fw.createTable();
		assertEquals("Testing maxDist() function", 1860, fw.timeRequiredforCompetition() * 60, 0.0000001);
	}
	
	@Test
	public void testFWTimeRequiredForCompetition() {
		CompetitionFloydWarshall fw = new CompetitionFloydWarshall("tinyEWD.txt", 60, 70, 80);
		fw.createTable();
		assertEquals("Testing maxDist() function", 31, fw.timeRequiredforCompetition(), 0.0000001);
	}
}