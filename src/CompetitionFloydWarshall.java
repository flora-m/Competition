import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/*
 * A Contest to Meet (ACM) is a reality TV contest that sets three contestants at three random
 * city intersections. In order to win, the three contestants need all to meet at any intersection
 * of the city as fast as possible.
 * It should be clear that the contestants may arrive at the intersections at different times, in
 * which case, the first to arrive can wait until the others arrive.
 * From an estimated walking speed for each one of the three contestants, ACM wants to determine the
 * minimum time that a live TV broadcast should last to cover their journey regardless of the contestants’
 * initial positions and the intersection they finally meet. You are hired to help ACM answer this question.
 * You may assume the following:
 *     Each contestant walks at a given estimated speed.
 *     The city is a collection of intersections in which some pairs are connected by one-way
 * streets that the contestants can use to traverse the city.
 *
 * This class implements the competition using Floyd-Warshall algorithm
 * 
 *  @author Barbara Flora Molnar
 */

public class CompetitionFloydWarshall {


	double table[][];
	int sA, sB, sC;
	String file;
	int numOfIntersections, numOfStreets;

	/**
	 * @param filename: A filename containing the details of the city road network
	 * @param sA, sB, sC: speeds for 3 contestants
	 */
	CompetitionFloydWarshall (String filename, int sA, int sB, int sC) {

		this.sA = sA;
		this.sB = sB;
		this.sC = sC;
		this.file = filename;
	}

	public int createTable() {

		if(!isValidSpeed(sA) || !isValidSpeed(sB) || !isValidSpeed(sC))
		{
			return -1;
		}

		try 
		{	
			BufferedReader reader = new BufferedReader(new FileReader(file)); 

			String line = reader.readLine();
			// check if file is empty
			if(line == null)
			{
				reader.close();
				return -1;
			}
			numOfIntersections = Integer.parseInt(line); // number of intersections
			line = reader.readLine();
			numOfStreets = Integer.parseInt(line);	// number of streets
			line = reader.readLine();

			// check if file information is valid
			if(numOfIntersections == 0 || numOfStreets == 0)
			{
				reader.close();
				return -1;
			}

			// create 2D int array to store information from the file
			table = new double [numOfIntersections][numOfIntersections];

			// intitalise values in table
			for(int i = 0; i < numOfIntersections; i++)
			{
				for(int j = 0; j < numOfIntersections; j++)

					// set distance from vertex to itself equal to 0
					if(i == j)
					{
						table[i][j] = 0;
					}

					// set everything else equal to ~infinity
					else
					{
						table[i][j] = Double.MAX_VALUE;
					}
			}

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

				// store distance from start to dest vertices in table
				table[start][dest] = length;
				//System.out.println(line); 
				line = reader.readLine();

			}
			reader.close();
		}
		catch (FileNotFoundException e) 
		{
			System.err.println("File not found.");
			e.printStackTrace();
			return -1;
		}
		catch(IOException e)
		{
			System.err.println("IO exception.");
			e.printStackTrace();
			return -1;
		}

		return 0;	
	}

	public void printTable() {

		for(int index = 0; index < table.length; index++)
		{
			for(int index2 = 0; index2 < table.length; index2++)
			{
				System.out.println(index + " " + index2 + ": " + table[index][index2]);
			}
		}
	}

	public boolean isValidSpeed(int speed)
	{
		if(speed < 50 || speed > 100)
		{
			return false;
		}
		return true;
	}


	public double maxDist()
	{
		double max = Double.MIN_VALUE;
		for(int i = 0; i < numOfIntersections; i++)
		{
			for(int j = 0; j < numOfIntersections; j++)
			{
				if(max < table[i][j])
				{
					max = table[i][j];
				}
			}
		}
		return max;

	}

	/**
	 * @return int: minimum minutes that will pass before the three contestants can meet
	 */
	public int timeRequiredforCompetition() {

		if(createTable() == -1)
		{
			return -1;
		}

		for(int k = 0; k < numOfIntersections; k++)
		{
			for(int i = 0; i < numOfIntersections; i++)
			{
				for(int j = 0; j < numOfIntersections; j++)
				{
					if(table[i][j] > table[i][k] + table[k][j])
					{
						table[i][j] = table[i][k] + table[k][j];
					}
				}
			}
		}
		 
		// get largest distance between two intersections (vertices)
		double max = maxDist();
//		System.out.println("max dist: " + max);
		
		// slowest walking speed
		int speedMin = Math.min(sA, sB);
		speedMin = Math.min(speedMin, sC);
//		System.out.println("speedMin: " + speedMin);
		
		// time required for slowest person to walk largest possible distance
		int time = (int) Math.ceil(max / (double) speedMin);
//		System.out.println("time: " + time);
		return time;


	}

//	public static void main(String[] args) {
//
//		String file = "C:\\Users\\flora\\Documents\\GitHub\\Shortest-Path-Algorithms\\data\\tinyEWD.txt";
//		int a = 60;
//		int b = 70;
//		int c = 80;
//		CompetitionFloydWarshall fw = new CompetitionFloydWarshall(file, a, b, c);
//		fw.createTable();
//		fw.timeRequiredforCompetition();
//		fw.printTable();
//
//	}

}