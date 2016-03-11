import java.util.Map;

public class GraphFunctions {

	private int nodeNumber;
	private int visited;
	private Integer distance = null;
	private int source_number;
	private int srcforPath;

	public void setsrcforPath(int source){

		this.srcforPath = source;
	}
	public int getsrcforPath(){

		return srcforPath;
	}
	public void setSrc_Num(int source){

		this.source_number = source;
	}
	public int getSrc_Num(){

		return source_number;
	}
	public Integer getDist() {
		return distance;
	}
	public void setDist(Integer distance) // setting distance
	{
		this.distance = distance;
	}
	public int getnNum() {
		return nodeNumber;
	}
	public void setRN(int nodeNumber) {
		this.nodeNumber = nodeNumber;
	}

	public int isVisited() {
		return visited;
	}
	public void setVisited(int i) {

		this.visited = i;
		}
	public static void initRouters(int b, int a, int minDistance, int nRouter, GraphFunctions[][] distN) {
		distN[b][a].setDist(minDistance);
		distN[b][a].setRN(nRouter);
	}
	public static void visits(int b, int a, GraphFunctions[][] distN) {
		distN[b][a].setDist(distN[b-1][a].getDist());
		distN[b][a].setRN(distN[b-1][a].getnNum());
	}
	public static void visitsnew(int b, int a, GraphFunctions[][] distN) {
		distN[b][a].setDist(distN[b-1][a].getDist());
		distN[b][a].setRN(distN[b-1][a].getnNum());
	}
	public static void setValues(int b, int a, int newDistance, int nRouter, GraphFunctions[][] distN) {
			distN[b][a].setDist(newDistance);
			distN[b][a].setRN(nRouter);

	}
	public static void setValuesnew(GraphFunctions[][] distN, int a, int b) {

		distN[b][a].setDist(distN[b-1][a].getDist());
		distN[b][a].setRN(distN[b-1][a].getnNum());
	}
	public static void addToConnTable(int lineNumber, GraphFunctions[][] distN,
			Map<Integer, DijkstraFunctions> connectionTable) {

			int a = 0;
			for(int b =1;b<=lineNumber; b++){

				int nodeNumber = distN[b][a].getnNum();
				int distance = distN[b][a].getDist();

				DijkstraFunctions node = new DijkstraFunctions();
				node.setDistance(distance);
				node.setNNmber(nodeNumber);

				connectionTable.put(nodeNumber, node);
			}
	}


}
