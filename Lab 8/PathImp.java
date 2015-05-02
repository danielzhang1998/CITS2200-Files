import java.util.Arrays;
import java.util.PriorityQueue;
import CITS2200.Graph;
import CITS2200.IllegalValue;
import CITS2200.Path;




/**
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
		if(g == null) throw new NullPointerException("Graph is empty.");
		int numVertices = g.getNumberOfVertices();
		int[] colour = new int[numVertices];
		int[] parent = new int[numVertices];
		//	Set parents as unknown
		Arrays.fill(parent,-1);
		int[] distance = new int[numVertices];
		//	Set distances / weights as unknown, in this case infinity
		//	Need to use a positive unreachable as looking for shortest
		Arrays.fill(distance, Integer.MAX_VALUE);
		
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
			
			//	Now has been seen
			colour[breadSlice] = 1;
 					
			for(int i = 0; i < numVertices; ++i){
				int edgeCost = sliceWeight[breadSlice][i];
				if(breadSlice != i && edgeCost > 0 && colour[i] <1){
					//	If current noted distance to i > distance to parent + edge
					//	replace distance for i and place in priority queue
					//	This takes care of unfound vertices and selecting the
					//	minimum cost vertex from a point at the same time
					//	Keeps a cumulative total going.
					if(distance[i] > edgeCost){
						distance[i] = edgeCost;
						parent[i] = breadSlice;
						toaster.add(new Edge(i,edgeCost));
					}
				}
			}
		}
 		int mstWeight = 0;
 		for(int j : distance){
 			if(j == Integer.MAX_VALUE){
 				mstWeight = -1;
 				break;
 			}
 			mstWeight += j;
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
		if(g == null) throw new NullPointerException("Graph is empty.");
		int numVertices = g.getNumberOfVertices();
		int[] colour = new int[numVertices];
		int[] parent = new int[numVertices];
		//	Set parents as unknown
		Arrays.fill(parent,-1);
		int[] distance = new int[numVertices];
		//	Set distances / weights as unknown, in this case infinity
		//	Need to use a positive unreachable as looking for shortest
		Arrays.fill(distance, Integer.MAX_VALUE);
		
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
			//	Mark vertex from queue as seen
			colour[breadSlice] = 1;
			
			for(int i = 0; i < numVertices; ++i){
				int edgeCost = sliceWeight[breadSlice][i];
				if(edgeCost > 0 && colour[i] <1){
					//	If current noted distance to i > distance to parent + edge
					//	replace distance for i and place in priority queue
					//	This takes care of unfound vertices and selecting the
					//	minimum cost vertex from a point at the same time
					//	Keeps a cumulative total going.
					if(distance[i] > distance[breadSlice] + edgeCost){
						distance[i] = distance[breadSlice] + edgeCost;
						parent[i] = breadSlice;
						toaster.add(new Edge(i,edgeCost));
					}
				}
			}
		}
 		for(int k=0; k < numVertices; ++k){
 			if(distance[k] == Integer.MAX_VALUE){
 				distance[k] = -1;
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
		
		
		@Override
		public int compareTo(Edge otherEdge) {
			int otherWeight = otherEdge.edgeWeight;
			
			if(edgeWeight < otherWeight){
				return -1;
			}
			else if(edgeWeight > otherWeight){
				return 1;
			}
			else return 0;
		}
	}
}
