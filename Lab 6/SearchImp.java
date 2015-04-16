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
	
	
	public void bfs(Graph g, int v){
		
		int vertices = g.getNumberOfVertices();
		//white = 0, grey = 1, black = 2
		int[] colour = new int[vertices];
		int[] parent = new int[vertices];
		
		for
		
		Queue<Integer> flow = new LinkedList<Integer>();
		
		flow.add(v);
		
		while(!flow.isEmpty()){
			int focus = flow.poll();
			for(int i = 0; i < vertices; ++i){
				if (g.getEdgeMatrix()[focus][i] == 1){
					if(colour[i] == 0){
						parent[i] = focus;
						colour[i] = 1;
						flow.add(i);
					}
				}
			}
			colour[v] = 2;
			}
		}
	
	

	@Override
	public int[] getConnectedTree(Graph g, int startV) {
		int vertices = g.getNumberOfVertices();
		int[] parents = new int[vertices];
		for(int i = 0; i < vertices; i++){
			
		}
		return null;
	}

	@Override
	public int[] getDistances(Graph arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[][] getTimes(Graph arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
