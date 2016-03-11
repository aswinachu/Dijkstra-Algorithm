import java.util.Iterator;

public class shortestPATH extends Dijkstra  {
	static void shortPath(int dt, Iterator<Integer> itr22)  // functioned called by main to call connection table to find shortest path
	{
		int tempSize = Dijkstra.matrixSize;
		int tempDist = Dijkstra.maxDistance;

		if (dt <=tempSize && dt>0)
		{
			while(itr22.hasNext())
			{
				Integer n = itr22.next();
				if(dt != n)
				{
					continue;
				}
				DijkstraFunctions node = new DijkstraFunctions();
				node = connectionTable.get(n);

				if(node.getDistance() == tempDist)
				{
					System.out.println("Shortest distance to " + node.getNNmber() + " is infinity and is not reachable ");
				}
				else
				{
					System.out.println("The shortest path from to router " + node.getNNmber() + " is " +node.getShortDIST()+", the total cost is "+ node.getDistance()+"\n");
				}
			}
		}
		else
		{
			System.out.println("Enter correct destination adddress  router "+dt+ "  does not exist\n");
		}
	}

	public static String rv(String s) {
		if (s.length() <= 1) {
			return s;
		}
		return rv(s.substring(1, s.length())) + s.charAt(0);
	}

	public static void initNodes(int lineNumber2, int source_router2, GraphFunctions[][] distN2)
	{
		int src_rt = source_router2;
		int linnbr = lineNumber2;
		int a,b=0;
		for(a = 0; a<=linnbr; a++){  //initialization
			if(b ==0 && a ==0){
				distN[b][a].setDist(0);
				distN[b][a].setRN(0);
			}
			else if(a== src_rt){
				distN[b][a].setDist(0);
				distN[b][a].setRN(a);
			}
			else{
				distN[b][a].setDist(maxDistance);
				distN[b][a].setRN(-1);
			}
		}
	}
}
