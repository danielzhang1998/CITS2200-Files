import java.util.Arrays;
import java.util.PriorityQueue;
import CITS2200.Graph;
import CITS2200.IllegalValue;
import CITS2200.Path;




/**
 * Examples of using Dijkstra's and Prim's Algorithms
 * Implementations
 * Could have one process for both as they are almost
 * the same greedy BFS algorithm except that Dijkstra's
 * accumulates while Prim's simply retains the minimum
 * edge.
 * @author Pradyumn
 *
 */
public class PathImp implements Path{
	
	@Override
	/*
	 *	Implementation of Prim's to find Minimum
	 *	Spanning Tree.  Essentially a BFS that checks
	 *	edges with undiscovered vertices and selects
	 *	the edge with the minimal distance to select
	 *	which vertex to add to the stack. 
	 *
	 */
	public int getMinSpanningTree(Graph g) {
		//		Check if graph is null
		int numVertices = g.getNumberOfVertices();
		if(numVertices == 0) throw new IllegalValue("Graph is empty.");
		int[] colour = new int[numVertices];
		int[] parent = new int[numVertices];
		//	Set parents as unknown
		Arrays.fill(parent,-1);
		int[] distance = new int[numVertices];
		//	Set distances / weights as unknown, use -1
		//	assuming non-negative edges not provided
		Arrays.fill(distance, -1);
		
		//	Assign a variable for the adjacency matrix for a single function
		//	call
 		int[][] sliceWeight = g.getEdgeMatrix();
 		//	Priority Queue of size numVertices
 		PriorityQueue<Edge> toaster = new PriorityQueue<Edge>();
		//	Add starting point to priority queue, with priority represented by
 		//	distance
 		
 		//arbitrarily starting at 0
 		toaster.add(new Edge(0, 0));
 		//	Arbitrarily use 0 as starting vertex, set distance to self
 		//	as 0 and colour as found.
		distance[0] = 0;
		
 		while(!toaster.isEmpty()){
 			//	Take head of priority queue
			//	Extract vertex from edge			
			int breadSlice = toaster.remove().vertex;
			
			//	Check if already processed, if processed
			//	skip iteration
			if(colour[breadSlice] != 0) continue;
			
			//	Now has been seen / processed
			colour[breadSlice] = 1;
 					
			for(int i = 0; i < numVertices; ++i){
				int edgeCost = sliceWeight[breadSlice][i];
				if(edgeCost > 0 && colour[i] <1){
					//	If current noted distance to i > distance to parent + edge
					//	replace distance for i and place in priority queue
					//	This takes care of unfound vertices and selecting the
					//	minimum cost vertex from a point at the same time
					//	Keeps a cumulative total going.
					if(distance[i] == -1 || distance[i] > edgeCost){
						distance[i] = edgeCost;
						parent[i] = breadSlice;
						toaster.add(new Edge(i,distance[i]));
					}
				}
			}
		}
 		int mstWeight = 0;
 		for(int j : distance){
 			if(j == -1){
 				mstWeight = -1;
 				break;
 			}
 			mstWeight+=j;
 		}
		return mstWeight;
	}
	
	/**
	 * 	Example of Djikstra's algorithm to find the shortest path
	 * 	in a weighted, non-negative edge, undirected graph.
	 * 	Returns the shortest distance / cost / weight from the 
	 * 	selected start vertex to each other vertex in graph.
	 */
	@Override
	public int[] getShortestPaths(Graph g, int startVertex) {
		//	Check if graph is null
		int numVertices = g.getNumberOfVertices();
		if(numVertices == 0) throw new IllegalValue("Graph is empty.");
		int[] colour = new int[numVertices];
		int[] parent = new int[numVertices];
		//	Set parents as unknown
		Arrays.fill(parent,-1);
		int[] distance = new int[numVertices];
		//	Set distances / weights as unknown, in this case -1
		//	Known that Dijkstra's can't be used for negative weighted
		//	graphs, assumption that there will be none in this case
		//	instead of using infinity to make output easily accessible
		//	for test.
		//	Need to use a positive unreachable as looking for shortest
		Arrays.fill(distance, -1);
		
		//	Assign a variable for the adjacency matrix for a single function
		//	call
 		int[][] sliceWeight = g.getEdgeMatrix();
 		//	Priority Queue of size numVertices
 		PriorityQueue<Edge> toaster = new PriorityQueue<Edge>();
		//	Add starting point to priority queue, with priority represented by
 		//	distance
 		toaster.add(new Edge(startVertex, 0));
 		//	Arbitrarily use 0 as starting vertex, set distance to self
 		//	as 0 and colour as found.
		distance[startVertex] = 0;
 		while(!toaster.isEmpty()){
 			//	Take head of priority queue
			//	Extract vertex from edge			
			int breadSlice = toaster.remove().vertex;
			
			//	Check if already processed, if processed
			//	skip iteration
			if(colour[breadSlice] != 0) continue;
			//	Mark vertex from queue as processed
			colour[breadSlice] = 1;

			for(int i = 0; i < numVertices; ++i){
				int edgeCost = sliceWeight[breadSlice][i];
				if(edgeCost > 0 && colour[i] <1){
					//	If current noted distance to i > distance to parent + edge
					//	replace distance for i and place in priority queue
					//	This takes care of unfound vertices and selecting the
					//	minimum cost vertex from a point at the same time
					//	Keeps a cumulative total going.
					if(distance[i] == -1 || distance[i] > distance[breadSlice] + edgeCost){
						distance[i] = distance[breadSlice] + edgeCost;
						parent[i] = breadSlice;
						toaster.add(new Edge(i,distance[i]));
					}
				}
			}
		}
 		return distance;
	}
	
	/**
	 * 	Internal class Edge to represent edges and 
	 * 	allows Priority Queue to compare edge weights.
	 */
	private class Edge implements Comparable<Edge>{
		//	integer that represents the vertex
		public int vertex;
		//	integer that will represent the edge weight
		public int edgeWeight;
		
		public Edge(int endVertex, int cost){
			vertex = endVertex;
			edgeWeight = cost;
		}
		
		//	Comparisons that give higher priority to lower
		//	valued edges.
		@Override
		public int compareTo(Edge current) {
			int currentWeight = current.edgeWeight;
			
			if(edgeWeight < currentWeight){
				return -1;
			}
			else if(edgeWeight > currentWeight){
				return 1;
			}
			else return 0;
		}
	}
}
