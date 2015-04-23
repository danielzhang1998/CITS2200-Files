import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import CITS2200.Graph;
import CITS2200.Search;

/**
 * 
 */

/**
 * @author Pradyumn
 *
 */
public class SearchImp implements Search {
	
	//make a list to pass along int[] from BFS
	private final static LinkedList<int[]> result = new LinkedList<int[]>();
	
	
	@Override
	public int[] getConnectedTree(Graph g, int startVertex) {
		beforeFirst(g,startVertex);
		return result.get(0);
	}
	
	
	@Override
	public int[] getDistances(Graph g, int startVertex) {
		beforeFirst(g,startVertex);
		return result.get(1);
		}
	
	public void beforeFirst(Graph g, int startVertex) {
		//call for vertex number and adjacency matrix only once
		int[][] adjacencyMatrix = g.getEdgeMatrix();
		int vertices = g.getNumberOfVertices();
		//white = 0 not found, grey = 1 found, black = 2 processed
		int[] colour = new int[vertices];
		//startVertex has already been found
		colour[startVertex] = 1;
		
		//Initiate parent array with -1 as being unfound
		int[] parent = new int[vertices];	
		Arrays.fill(parent, -1);
		
		//Initiate distance array with -1 except startVertex
		//which has zero distance to itself.
		int[] distance = new int[vertices];
		Arrays.fill(distance,-1);
		
		//Queue to temporarily hold vertices
		Queue<Integer> flow = new LinkedList<Integer>();
		
		//Add starting node to queue
		flow.add(startVertex);
		//distance to self is 0
		distance[startVertex] = 0;
		
		while(!flow.isEmpty()){
			int focus = flow.poll();
			for(int i = 0; i < vertices; ++i){
				if (adjacencyMatrix[focus][i] > 0 && colour[i] < 1){
					distance[i] = distance[focus] + 1;
					parent[i] = focus;
					colour[i] = 1;
					flow.add(i);
				}
			}
		}
	result.add(0,parent);
	result.add(1,distance);
	}
	
	
	@Override
	public int[][] getTimes(Graph g, int startVertex) {
		int vertices = g.getNumberOfVertices();
		//Create 2 column array for start and finish times
		int[][] times = new int[vertices][2];
		// all vertices are unfound
		int[] colour = new int[vertices];
		// set timer to 0
		int timer = 0;
		//Begin DFS
		for(int i = 0; i<vertices; ++i){
			if(colour[i] < 1){
				//pass along variables to avoid creating globals
				depthFirstTimer(g, startVertex, colour, times, timer);
			}
		}
		return times;
	}
	

	
	private void depthFirstTimer(Graph g, int currentVertex, int[]colour, int[][]times, int timer){
		int vertices = g.getNumberOfVertices();
		//mark vertex as found
		colour[currentVertex] = 1;
		//call function only once
		int[][] adjacencyMatrix = g.getEdgeMatrix();
		//increment timer for finding vertex and add as start
		times[currentVertex][0] = ++timer;
		for(int i = 0; i < vertices; ++i){
			//if vertex is adjacent and colour is white
			//add vertex to recursive stack with timer
			if(adjacencyMatrix[currentVertex][i] > 0 && colour[i] < 1){
				depthFirstTimer(g, i, colour, times, timer);
			}
		}
		//mark vertex as processed, DFS finished, not really required
		colour[currentVertex] = 2;
		//increment timer and add as end time
		times[currentVertex][1] = ++timer;
	}
	
}