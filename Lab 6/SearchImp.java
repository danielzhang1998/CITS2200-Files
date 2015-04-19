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
		for(int p:parent){
			p = -1;
		}
		
		//Queue to temporarily hold nodes
		Queue<Integer> flow = new LinkedList<Integer>();
		
		//Add starting node to queue
		flow.add(startVertex);
		
		//Indicate which vertex is being looked for
		int arrayPos = 1;
		while(!flow.isEmpty()){
			int focus = flow.poll();
			for(int i = 0; i < g.getEdgeMatrix()[0].length; ++i){
				if (g.getEdgeMatrix()[i][0] == focus){
					if(colour[arrayPos] == 0){
						parent[arrayPos] = focus;
						colour[arrayPos] = 1;
						++arrayPos;
						flow.add(g.getEdgeMatrix()[i][1]);
					}
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
		for(int p:parent){
			p = -1;
		}
		
		//Initiate parent array with -1 as being unfound
		int[] distance = new int[vertices];
		
		// automatically designates startVertex as having zero distance
		int j = 1;
		while(j<vertices){
			distance[j] = -1;
		}
		
		//Queue to temporarily hold nodes
		Queue<Integer> flow = new LinkedList<Integer>();
		
		//Add starting node to queue
		flow.add(startVertex);
		
		//Indicate which vertex is being looked for
		int arrayPos = 1;
		while(!flow.isEmpty()){
			int focus = flow.poll();
			for(int i = 0; i < g.getEdgeMatrix()[0].length; ++i){
				if (g.getEdgeMatrix()[i][0] == focus){
					if(colour[arrayPos] == 0){
						parent[arrayPos] = focus;
						distance[arrayPos] = distance[Arrays.asList(distance).indexOf(focus)] + 1;
						colour[arrayPos] = 1;
						++arrayPos;
						flow.add(g.getEdgeMatrix()[i][1]);
					}
				}
			}
		}
	return distance;
	}

	
	
	@Override
	public int[][] getTimes(Graph g, int startVertex) {
		int vertices = g.getNumberOfVertices();
		//Create 2 column array for start and finish times
		int[][] times = new int[vertices][2];
		int[] colour = new int[vertices];
		//Initiate parent array with -1 as being unfound
		int[] parent = new int[vertices];
		for(int p:parent){
			p = -1;
		}
		
		int arrayPos = 0;
		int timer = 0;
		
		depthFirstTiming(g, colour, parent, arrayPos, timer, startVertex, times);
		
		
		return null;
	}
	
	private void  depthFirstTiming(Graph g, int[] colour, int[] parent, int timer, int arrayPos, int start, int[][]times){
		times[arrayPos][0] = ++timer;
		colour[start] = 1;
		for(int i = 0; i < g.getEdgeMatrix()[0].length; ++i){
			if (g.getEdgeMatrix()[i][0] == start && colour[arrayPos] == 0){
				parent[arrayPos] = start;
				depthFirstTiming(g, colour, parent, timer, arrayPos, g.getEdgeMatrix()[i][1], times);
			}
		}
		colour[arrayPos] = 2;
		times[arrayPos][1] = ++timer;
	}

}
