/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestantsâ€™
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *    · Each contestant walks at a given estimated speed.
 *    · The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Dijkstra's algorithm
 */
import java.io.File; 
import java.io.FileNotFoundException;
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map; 

public class CompetitionDijkstra {


	Map<Integer, Node> map;

	private class Node {

		private int from;
		private ArrayList<Street> streets;

		public Node(int from, int to, double dist)
		{
			this.from = from;
			Street s = new Street(to, dist);

			this.streets = new ArrayList<Street>();
			this.streets.add(s);
		}

		public void addStreet(int to, double dist)
		{
			Street s = new Street(to, dist);
			streets.add(s);
		}

		public int getFrom() 
		{
			return from;
		}

		public ArrayList<Street> getStreets() 
		{
			return streets;
		}
	}

	private class Street {
		private int to;
		private double dist;

		public Street(int to, double dist)
		{
			this.to = to;
			this.dist = dist;
		}

		public int getTo() 
		{
			return to;
		}

		public double getDist() 
		{
			return dist;
		}
	}

	/**
	 * @param filename: A filename containing the details of the city road network
	 * @param sA, sB, sC: speeds for 3 contestants
	 */
	CompetitionDijkstra (String filename, int sA, int sB, int sC) {

		map = new HashMap<Integer, Node>();
		createMap(filename);
	}
	
	private void createMap(String filename) {
		
		try 
		{
			
			File file = new File(filename); 
			BufferedReader reader = new BufferedReader(new FileReader(file)); 

			String line = reader.readLine();
			int N = Integer.parseInt(line); // number of intersections
			line = reader.readLine();
			int S = Integer.parseInt(line);	// number of streets
			line = reader.readLine();

			while (line != null) 
			{
				String i = line.split(" ")[0];
				int start = Integer.parseInt(i);
				System.out.println(start);

				String j = line.split(" ")[1];
				int dest = Integer.parseInt(j);
				System.out.println(dest);

				String k = line.split(" ")[2];
				double length = Double.parseDouble(k) * 1000;
				System.out.println(length);

				Node node1;
				if(map.containsKey(start))
				{
					map.get(start).addStreet(dest, length);
				
				else
				{
					node1 = new Node(start, dest, length);
					map.put(start, node1);
				}
				
				//System.out.println(line); 
				line = reader.readLine();
			}
			reader.close();
		}
		catch (FileNotFoundException e) 
		{
			System.err.println("File not found.");
			e.printStackTrace();
		}
		catch(IOException e)
		{
			System.err.println("IO exception.");
			e.printStackTrace();
		}
	}


	/**
	 * @return int: minimum minutes that will pass before the three contestants can meet
	 */
	public int timeRequiredforCompetition(){

		//TO DO
		return -1;
	}


	public static void main(String[] args)
	{
		String file = "C:\\Users\\flora\\Documents\\GitHub\\Shortest-Path-Algorithms\\data\\tinyEWD.txt";
		// walking speed of contestants
		int a = 84;
		int b = 62;
		int c = 76;
		CompetitionDijkstra dijkstra = new CompetitionDijkstra(file, a, b, c);

	}

}
