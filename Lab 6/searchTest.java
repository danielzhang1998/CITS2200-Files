import CITS2200.Graph;


public class searchTest {

	public static void main(String[] args) {
		Graph g = null;
		
		SearchImp s = new SearchImp();
		
		Graph r = g.randomGraph(5, false,0.5);
		
		int vertices = r.getNumberOfVertices();
		
		System.out.println("Number of Vertices: "+vertices);
		
		for(int i = 0; i < vertices; ++i){
			for(int j = 0; j < vertices; ++j){
				System.out.print(r.getEdgeMatrix()[i][j] + " ");
			}
			System.out.println("\n");
		}
		
		
		System.out.println("Parent matrix: \n");
		for(int p : s.getConnectedTree(r, 0)){
			System.out.println(p);
		}
		
		
		System.out.println("Distance matrix: \n");
		for(int p : s.getDistances(r, 0)){
			System.out.println(p);
		}
		
		System.out.println("Times by Discovery and End: \n");
			for(int k = 0; k < vertices ;k++){
				System.out.println(s.getTimes(r,0)[0] + " " + s.getTimes(r,0)[1]);
			}
		}

	}


