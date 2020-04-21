/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random

 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestants.
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *    · Each contestant walks at a given estimated speed.
 *    · The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Dijkstra's algorithm
 * 
 *  @author Barbara Flora Molnar
 */
import java.io.File; 

import java.io.FileNotFoundException;
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class CompetitionDijkstra {


	Map<Integer, Node> map;
	LinkedList<Node> unvisited;
	int sA, sB, sC;
	String filename;

	private class Node {

		private int from;
		private ArrayList<Street> streets;
		private double distFromSource;

		public Node(int from)
		{
			this.from = from;
			//Street s = new Street(to, dist);

			this.streets = new ArrayList<Street>();
			//his.streets.add(s);


			// initialise cost of node
			//this.dist = Double.MAX_VALUE;
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

		public double getDistFromSource() {
			return distFromSource;
		}

		public void setDistFromSource(double distFromSource) {
			this.distFromSource = distFromSource;
		}

		@Override
		public String toString() { 
			return String.format(from + ", " +  streets.toString() + ", " + distFromSource); 
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

		@Override
		public String toString() { 
			return String.format("street(" + to + ", " + dist + ")"); 
		} 
	}

	/**
	 * @param filename: A filename containing the details of the city road network
	 * @param sA, sB, sC: speeds for 3 contestants
	 */
	CompetitionDijkstra (String filename, int sA, int sB, int sC) 
	{
		this.sA = sA;
		this.sB = sB;
		this.sC = sC;
		this.filename = filename;
		map = new HashMap<Integer, Node>();
	}

	public int createMap() 
	{
		if(!isValidSpeed(sA) || !isValidSpeed(sB) || !isValidSpeed(sC))
		{
			return -1;
		}

		if(isValidFile(filename) == false)
		{
			return -1;
		}

		try 
		{	
			BufferedReader reader = new BufferedReader(new FileReader(filename)); 

			String line = reader.readLine();
			int N = Integer.parseInt(line); // number of intersections
			line = reader.readLine();
			int S = Integer.parseInt(line);	// number of streets
			line = reader.readLine();

			while (line != null) 
			{
				String i = line.split(" ")[0];
				int start = Integer.parseInt(i); // from
				//System.out.println(start);

				String j = line.split(" ")[1];
				int dest = Integer.parseInt(j);	// to
				//System.out.println(dest);

				String k = line.split(" ")[2];
				double length = Double.parseDouble(k) * 1000; // distance in meters
				//System.out.println(length);

				Node node1, node2;
				if(map.containsKey(start))
				{
					map.get(start).addStreet(dest, length);

					//System.out.println("Node 0: " + map.get(start));
				}

				else
				{
					node1 = new Node(start);
					map.put(start, node1);
					node1.addStreet(dest, length);

					//System.out.println("Node 1: " + node1);

					if(map.containsKey(dest) == false)
					{
						node2 = new Node(dest);
						map.put(dest, node2);

						//System.out.println("Node 2: " + node2);
					}
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

		return 0;

	}

	//	public void printMap()
	//	{
	//		for(Node node : map.values())
	//		{
	//			System.out.println("node: " + node);
	//		}
	//	}

	public void reset()
	{
		unvisited = new LinkedList<Node>();

		for(Node node : map.values())
		{
			node.setDistFromSource(Double.MAX_VALUE);
			unvisited.add(node);
		}
	}

	public void calcDistFromSource(int from)
	{
		map.get(from).setDistFromSource(0);

		while(unvisited.size() != 0)
		{
			double smallestDist = unvisited.element().getDistFromSource();
			Node smallestNode = unvisited.element();

			for(Node node : unvisited)
			{
				if(smallestDist > node.getDistFromSource())
				{
					smallestDist = node.getDistFromSource();
					smallestNode = node;
				}

				//				System.out.println("halos");
			}
			//			System.out.println("smol: " + smallestNode.getFrom());
			//
			//			System.out.println("halos out");

			for(Street street : smallestNode.streets)
			{
				double newDist = smallestNode.getDistFromSource() + street.getDist();

				// compare new route to stored route and replace if new route is shorter
				if(newDist < map.get(street.getTo()).getDistFromSource())
				{
					map.get(street.getTo()).setDistFromSource(newDist);
				}
				//				System.out.println("blegh");
			}

			unvisited.remove(smallestNode);

		}
		//		System.out.println("while out");


	}

	// calculate the largest possible distance from from the source vertex
	public double maxDist()
	{
		int destination = -1;
		double maxDist = -1.0;
		for(Node node : map.values())
		{
			if(maxDist < node.getDistFromSource())
			{
				maxDist = node.getDistFromSource();
				destination = node.getFrom();
				//				System.out.println("maxNode: " + node.getFrom());
			}
		}
		//		System.out.println("max distance: " + maxDist + ", destination: " + destination);
		return maxDist;
	}

	/**
	 * @return int: minimum minutes that will pass before the three contestants can meet
	 */
	public int timeRequiredforCompetition() 
	{
		double totalMax = 0.0;
		int fromMax = -1;
		int speedMin = Math.min(sA, sB);
		speedMin = Math.min(speedMin, sC);

		if(createMap() == -1)
		{
			return -1;
		}

		for(Node node : map.values())
		{
			reset();
			calcDistFromSource(node.getFrom());
			double max = maxDist();
			if(totalMax < max)
			{
				totalMax = max;
				fromMax = node.getFrom();
//				System.out.println("loop max: " + totalMax + " loop node: " + fromMax);
			}
		}

		//		System.out.println("source node: " + fromMax + " total max: " + totalMax); 
		//		double speed = Double.parseToDouble()
		int time = (int) Math.ceil(totalMax / (double) speedMin);
		//		System.out.println("min time: " + time);
		return time;

	}


	public boolean isValidSpeed(int speed)
	{
		if(speed < 50 || speed > 100)
		{
			return false;
		}
		return true;
	}

	public boolean isValidFile(String filename)
	{
		File tmp = new File(filename);
		if(tmp.exists())
		{
			System.out.println("true");
			return true;
		}
		System.out.println("false");
		return false;
	}


	//	public static void main(String[] args)
	//	{
	//		String file = "C:\\Users\\flora\\Documents\\GitHub\\Shortest-Path-Algorithms\\data\\tinyEWD.txt";
	//		// walking speed of contestants
	//		int a = 60;
	//		int b = 70;
	//		int c = 80;
	//		CompetitionDijkstra dijkstra = new CompetitionDijkstra(file, a, b, c);
	//		int result = dijkstra.timeRequiredforCompetition();
	//		System.out.println(result);
	//		dijkstra.printMap();
	//	}

}
