/*
 *  @author Barbara Flora Molnar
 */
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CompetitionTests {

	@Test
	public void testDijkstraConstructor() {

		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyEWD.txt", 60, 70, 80);
		assertEquals("Testing constructor with valid data file", 80, dijkstra.sC);
	}

	@Test
	public void testDijkstraInvalidFile() {

		CompetitionDijkstra dijkstra = new CompetitionDijkstra("invalidText.txt", 60, 70, 80);
		assertEquals("Testing isValidFile with invalid data file", -1, dijkstra.timeRequiredforCompetition());		
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

	//	@Test
	//	public void testDijkstraValidSpeed() {
	//		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyEWD.txt", 60, 70, 80);
	//		assertEquals("Testing createMap with valid walking speed", 0, dijkstra.timeRequiredforCompetition());	
	//	}

	@Test
	public void testNoStreet() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyTest.txt", 60, 70, 80);
		assertEquals("Testing node with no streets", -1, dijkstra.timeRequiredforCompetition());
	}

	@Test
	public void testEmptyFile() {
		CompetitionDijkstra dijkstra = new CompetitionDijkstra("empty.txt", 60, 70, 80);
		assertEquals("Testing node with no streets", -1, dijkstra.timeRequiredforCompetition());
	}

//	@Test
//	public void testMaxDist() {
//		CompetitionDijkstra dijkstra = new CompetitionDijkstra("tinyTest.txt", 60, 70, 80);
//		dijkstra.createMap();
//		dijkstra.reset();
//		dijkstra.calcDistFromSource(0);
//		assertEquals("Testing node with no streets", -1, (int) dijkstra.maxDist());
//	}



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


}