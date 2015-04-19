import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import CITS2200.Graph;
import CITS2200.Search;
import CITS2200.Graph;

/**
 * 
 */

/**
 * @author Pradyumn
 *
 */
public class SearchImp implements Search {
	
	
	@Override
	public int[] getConnectedTree(Graph g, int startVertex) {
		int vertices = g.getNumberOfVertices();
		//white = 0 not found, grey = 1 found, black = 2 processed
		int[] colour = new int[vertices];
		//startVertex has already been found
		colour[0] = 1;
				
		//Initiate parent array with -1 as being unfound
		int[] parent = new int[vertices];
		for(int p = 0; p < vertices; ++p){
			parent[p] = -1;
		}
		
		//Queue to temporarily hold nodes
		Queue<Integer> flow = new LinkedList<Integer>();
		
		//Add starting node to queue
		flow.add(startVertex);
		
		//Indicate which vertex is being looked for
		while(!flow.isEmpty()){
			int focus = flow.poll();
			for(int i = 0; i < vertices; ++i){
				if (g.getEdgeMatrix()[focus][i] > 1 && colour[i] == 0){
					parent[i] = focus;
					colour[i] = 1;
					flow.add(i);
				}
			}
		}
	return parent;
	}

	@Override
	public int[] getDistances(Graph g, int startVertex) {
		int vertices = g.getNumberOfVertices();
		//white = 0 not found, grey = 1 found, black = 2 processed
		int[] colour = new int[vertices];
		//startVertex has already been found
		colour[0] = 1;
		
		//Initiate parent array with -1 as being unfound
		int[] parent = new int[vertices];	
		Arrays.fill(parent, -1);
		
		//Initiate distance array with -1 except startVertex
		//which has zero distance to itself.
		int[] distance = new int[vertices];
		Arrays.fill(distance,-1);
		distance[0] = 0;
		
		//Queue to temporarily hold vertices
		Queue<Integer> flow = new LinkedList<Integer>();
		
		//Add starting node to queue
		flow.add(startVertex);
		
		while(!flow.isEmpty()){
			int focus = flow.poll();
			for(int i = 0; i < vertices; ++i){
				if (g.getEdgeMatrix()[focus][i] > 0 && colour[i] == 0){
					distance[i] = distance[focus] + 1;
					parent[i] = focus;
					colour[i] = 1;
					flow.add(i);
				}
			}
		}
	return distance;
	}

	
	// no need for parents as not creating a parent array
	private int[] colour;
	private int[][] times;
	private int vertices;
	
	@Override
	public int[][] getTimes(Graph g, int startVertex) {
		vertices = g.getNumberOfVertices();
		//Create 2 column array for start and finish times
		times = new int[vertices][2];
		// all vertices are unfound
		colour = new int[vertices];
		//Initiate timer to start at 0
		int timer = 0;
		//Begin DFS
		depthFirstTimer(g, startVertex, timer);
		
		return times;
	}
	
	private void depthFirstTimer(Graph g, int startVertex, int timer){
		//mark vertex as found
		colour[startVertex] = 1;
		//increment timer for finding vertex and add as start
		times[startVertex][0] = ++timer;
		for(int i = 0; i < vertices; ++i){
			//if vertex is adjacent and colour is white
			//add vertex to recursive stack with timer
			if(g.getEdgeMatrix()[startVertex][i] > 0 && colour[i] == 0){
				depthFirstTimer(g,i,timer);
			}
		}
		//mark vertex as processed, DFS finished, not really required
		colour[startVertex] = 2;
		//increment timer and add as end time
		times[startVertex][1] = ++timer;
	}
	
}